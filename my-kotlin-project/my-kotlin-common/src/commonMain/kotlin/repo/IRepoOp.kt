package ru.otus.otuskotlin.mykotlin.common.repo

interface IRepoOp {
    suspend fun createOp(rq: DbOpRequest): IDbOpResponse
    suspend fun readOp(rq: DbOpIdRequest): IDbOpResponse
    suspend fun updateOP(rq: DbOpRequest): IDbOpResponse
    suspend fun deleteOp(rq: DbOpIdRequest): IDbOpResponse
    suspend fun searchOp(rq: DbOpFilterRequest): IDbOpsResponse
    companion object {
        val NONE = object : IRepoOp {
            override suspend fun createOp(rq: DbOpRequest): IDbOpResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun readOp(rq: DbOpIdRequest): IDbOpResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun updateOP(rq: DbOpRequest): IDbOpResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun deleteOp(rq: DbOpIdRequest): IDbOpResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun searchOp(rq: DbOpFilterRequest): IDbOpsResponse {
                throw NotImplementedError("Must not be used")
            }
        }
    }
}
