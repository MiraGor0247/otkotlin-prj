package ru.otus.otuskotlin.mykotlin.repo.tests

import ru.otus.otuskotlin.mykotlin.common.models.MkpOp
import ru.otus.otuskotlin.mykotlin.common.repo.*

class OpRepositoryMock(
    private val invokeCreateOp: (DbOpRequest) -> IDbOpResponse = { DEFAULT_OP_SUCCESS_EMPTY_MOCK },
    private val invokeReadOp: (DbOpIdRequest) -> IDbOpResponse = { DEFAULT_OP_SUCCESS_EMPTY_MOCK },
    private val invokeUpdateOp: (DbOpRequest) -> IDbOpResponse = { DEFAULT_OP_SUCCESS_EMPTY_MOCK },
    private val invokeDeleteOp: (DbOpIdRequest) -> IDbOpResponse = { DEFAULT_OP_SUCCESS_EMPTY_MOCK },
    private val invokeSearchOp: (DbOpFilterRequest) -> IDbOpsResponse = { DEFAULT_OPS_SUCCESS_EMPTY_MOCK },
): IRepoOp {
    override suspend fun createOp(rq: DbOpRequest): IDbOpResponse {
        return invokeCreateOp(rq)
    }

    override suspend fun readOp(rq: DbOpIdRequest): IDbOpResponse {
        return invokeReadOp(rq)
    }

    override suspend fun updateOP(rq: DbOpRequest): IDbOpResponse {
        return invokeUpdateOp(rq)
    }

    override suspend fun deleteOp(rq: DbOpIdRequest): IDbOpResponse {
        return invokeDeleteOp(rq)
    }

    override suspend fun searchOp(rq: DbOpFilterRequest): IDbOpsResponse {
        return invokeSearchOp(rq)
    }

    companion object {
        val DEFAULT_OP_SUCCESS_EMPTY_MOCK = DbOpResponseOk(MkpOp())
        val DEFAULT_OPS_SUCCESS_EMPTY_MOCK = DbOpsResponseOk(emptyList())
    }
}