package ru.otus.otuskotlin.mykotlin.repo.pg

import com.benasher44.uuid.uuid4
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import ru.otus.otuskotlin.mykotlin.common.helpers.asMkpError
import ru.otus.otuskotlin.mykotlin.common.models.*
import ru.otus.otuskotlin.mykotlin.common.repo.*
import ru.otus.otuskotlin.mykotlin.repo.common.IRepoOpInitializable


class RepoOpSql (
    properties: SqlProperties,
    private val randomUuid: () -> String = { uuid4().toString() }
) : IRepoOp, IRepoOpInitializable {
    private val opTable = OpTable("${properties.schema}.${properties.table}")

    private val driver = when {
        properties.url.startsWith("jdbc:postgresql://") -> "org.postgresql.Driver"
        else -> throw IllegalArgumentException("Unknown driver for url ${properties.url}")
    }

    private val conn = Database.connect(
        properties.url, driver, properties.user, properties.password
    )

    fun clear(): Unit = transaction(conn) {
        opTable.deleteAll()
    }

    private fun saveObj(op: MkpOp): MkpOp = transaction(conn) {
        val res = opTable
            .insert {
                it.to(op, randomUuid)
            }
            .resultedValues
            ?.map { opTable.from(it) }
        res?.first() ?: throw RuntimeException("BD error: insert statement returned empty result")
    }

    private suspend inline fun <T> transactionWrapper(crossinline block: () -> T, crossinline handle: (Exception) -> T): T =
        withContext(Dispatchers.IO) {
            try {
                transaction(conn) {
                    block()
                }
            } catch (e: Exception) {
                handle(e)
            }
        }

    private suspend inline fun transactionWrapper(crossinline block: () -> IDbOpResponse): IDbOpResponse =
        transactionWrapper(block) { DbOpResponseErr(it.asMkpError()) }

    override fun save(ops: Collection<MkpOp>): Collection<MkpOp> = ops.map { saveObj(it) }
    override suspend fun createOp(rq: DbOpRequest): IDbOpResponse = transactionWrapper {
        DbOpResponseOk(saveObj(rq.op))
    }

    private fun read(id: MkpOpId): IDbOpResponse {
        val res = opTable.selectAll().where {
            opTable.id eq id.asString()
        }.singleOrNull() ?: return errorNotFound(id)
        return DbOpResponseOk(opTable.from(res))
    }

    override suspend fun readOp(rq: DbOpIdRequest): IDbOpResponse = transactionWrapper { read(rq.id) }

    private suspend fun update(
        id: MkpOpId,
        lock: MkpOpLock,
        block: (MkpOp) -> IDbOpResponse
    ): IDbOpResponse =
        transactionWrapper {
            if (id == MkpOpId.NONE) return@transactionWrapper errorEmptyId

            val current = opTable.selectAll().where { opTable.id eq id.asString() }
                .singleOrNull()
                ?.let { opTable.from(it) }

            when {
                current == null -> errorNotFound(id)
                current.lock != lock -> errorRepoConcurrency(current, lock)
                else -> block(current)
            }
        }

    override suspend fun updateOP(rq: DbOpRequest): IDbOpResponse = update(rq.op.id, rq.op.lock) {
        opTable.updateReturning(where = { opTable.id eq rq.op.id.asString() }) {
            it.to(rq.op.copy(lock = MkpOpLock(randomUuid())), randomUuid)
        }.singleOrNull()
            ?.let { DbOpResponseOk(opTable.from(it)) }
            ?: errorNotFound(rq.op.id)
    }

    override suspend fun deleteOp(rq: DbOpIdRequest): IDbOpResponse = update(rq.id, rq.lock) {
        opTable.deleteWhere { id eq rq.id.asString() }
        DbOpResponseOk(it)
    }

    override suspend fun searchOp(rq: DbOpFilterRequest): IDbOpsResponse =
        transactionWrapper({
            val res = opTable.selectAll().where {
                buildList {
                    add(Op.TRUE)
                    if (rq.ownerId != MkpUserId.NONE) {
                        add(opTable.owner_id eq rq.ownerId.asString())
                    }
                    if (rq.opType != MkpPaidType.NONE) {
                        add(opTable.op_type eq rq.opType)
                    }
                    if (rq.titleFilter.isNotBlank()) {
                        add(
                            (opTable.title like "%${rq.titleFilter}%")
                        )
                    }
                }.reduce { a, b -> a and b }
            }
            DbOpsResponseOk(data = res.map { opTable.from(it) })
        }, {
            DbOpsResponseErr(it.asMkpError())
        })
}