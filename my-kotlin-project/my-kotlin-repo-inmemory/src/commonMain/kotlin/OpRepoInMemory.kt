package ru.otus.otuskotlin.mykotlin.repo.inmemory

import com.benasher44.uuid.uuid4
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.otus.otuskotlin.mykotlin.common.models.*
import ru.otus.otuskotlin.mykotlin.common.repo.*
import ru.otus.otuskotlin.mykotlin.repo.common.IRepoOpInitializable
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import ru.otus.otuskotlin.mykotlin.common.repo.exceptions.RepoEmptyLockException

class OpRepoInMemory(
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { uuid4().toString() },
): OpRepoBase(), IRepoOp, IRepoOpInitializable {
    private val mutex: Mutex = Mutex()
    private val cache = Cache.Builder<String, OpEntity>()
        .expireAfterWrite(ttl)
        .build()

    override fun save(ops: Collection<MkpOp>) = ops.map { op ->
        val entity = OpEntity(op)
        require(entity.id != null)
        cache.put(entity.id, entity)
        op
    }

    override suspend fun createOp(rq: DbOpRequest): IDbOpResponse = tryOpMethod {
        val key = randomUuid()
        val op = rq.op.copy(id = MkpOpId(key), lock = MkpOpLock(randomUuid()))
        val entity = OpEntity(op)
        mutex.withLock {
            cache.put(key, entity)
        }
        DbOpResponseOk(op)
    }

    override suspend fun readOp(rq: DbOpIdRequest): IDbOpResponse = tryOpMethod {
        val key = rq.id.takeIf { it != MkpOpId.NONE }?.asString() ?: return@tryOpMethod errorEmptyId
        mutex.withLock {
            cache.get(key)
                ?.let {
                    DbOpResponseOk(it.toInternal())
                } ?: errorNotFound(rq.id)
        }
    }

    override suspend fun updateOP(rq: DbOpRequest): IDbOpResponse = tryOpMethod {
        val rqOp = rq.op
        val id = rqOp.id.takeIf { it != MkpOpId.NONE } ?: return@tryOpMethod errorEmptyId
        val key = id.asString()
        val oldLock = rqOp.lock.takeIf { it != MkpOpLock.NONE } ?: return@tryOpMethod errorEmptyLock(id)

        mutex.withLock {
            val oldOp = cache.get(key)?.toInternal()
            when {
                oldOp == null -> errorNotFound(id)
                oldOp.lock == MkpOpLock.NONE -> errorDb(RepoEmptyLockException(id))
                oldOp.lock != oldLock -> errorRepoConcurrency(oldOp, oldLock)
                else -> {
                    val newOp= rqOp.copy(lock = MkpOpLock(randomUuid()))
                    val entity = OpEntity(newOp)
                    cache.put(key, entity)
                    DbOpResponseOk(newOp)
                }
            }
        }
    }

    override suspend fun deleteOp(rq: DbOpIdRequest): IDbOpResponse = tryOpMethod {
        val id = rq.id.takeIf { it != MkpOpId.NONE } ?: return@tryOpMethod errorEmptyId
        val key = id.asString()
        val oldLock = rq.lock.takeIf { it != MkpOpLock.NONE } ?: return@tryOpMethod errorEmptyLock(id)

        mutex.withLock {
            val oldOp = cache.get(key)?.toInternal()
            when {
                oldOp == null -> errorNotFound(id)
                oldOp.lock == MkpOpLock.NONE -> errorDb(RepoEmptyLockException(id))
                oldOp.lock != oldLock -> errorRepoConcurrency(oldOp, oldLock)
                else -> {
                    cache.invalidate(key)
                    DbOpResponseOk(oldOp)
                }
            }
        }
    }

    /**
     * Поиск объявлений по фильтру
     * Если в фильтре не установлен какой-либо из параметров - по нему фильтрация не идет
     */
    override suspend fun searchOp(rq: DbOpFilterRequest): IDbOpsResponse = tryOpsMethod {
        val result: List<MkpOp> = cache.asMap().asSequence()
            .filter { entry ->
                rq.ownerId.takeIf { it != MkpUserId.NONE }?.let {
                    it.asString() == entry.value.ownerId
                } ?: true
            }
            .filter { entry ->
                rq.opType.takeIf { it != MkpPaidType.NONE }?.let {
                    it.name == entry.value.opType
                } ?: true
            }
            .filter { entry ->
                rq.titleFilter.takeIf { it.isNotBlank() }?.let {
                    entry.value.title?.contains(it) ?: false
                } ?: true
            }
            .map { it.value.toInternal() }
            .toList()
        DbOpsResponseOk(result)
    }
}