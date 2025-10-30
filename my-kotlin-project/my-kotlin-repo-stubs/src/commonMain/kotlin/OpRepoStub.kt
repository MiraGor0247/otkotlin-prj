package ru.otus.otuskotlin.mykotlin.repo.stubs

import ru.otus.otuskotlin.mykotlin.common.models.MkpPaidType
import ru.otus.otuskotlin.mykotlin.common.repo.*
import ru.otus.otuskotlin.mykotlin.stubs.MkpOpStub

class OpRepoStub() : IRepoOp {
    override suspend fun createOp(rq: DbOpRequest): IDbOpResponse {
        return DbOpResponseOk(
            data = MkpOpStub.get(),
        )
    }

    override suspend fun readOp(rq: DbOpIdRequest): IDbOpResponse {
        return DbOpResponseOk(
            data = MkpOpStub.get(),
        )
    }

    override suspend fun updateOP(rq: DbOpRequest): IDbOpResponse {
        return DbOpResponseOk(
            data = MkpOpStub.get(),
        )
    }

    override suspend fun deleteOp(rq: DbOpIdRequest): IDbOpResponse {
        return DbOpResponseOk(
            data = MkpOpStub.get(),
        )
    }

    override suspend fun searchOp(rq: DbOpFilterRequest): IDbOpsResponse {
        return DbAdsResponseOk(
            data = MkpOpStub.prepareSearchList(title = "", num = "", MkpPaidType.UNPAID),
        )
    }
}