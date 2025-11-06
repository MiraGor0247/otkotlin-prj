package ru.otus.otuskotlin.mykotlin.common.repo

import ru.otus.otuskotlin.mykotlin.common.models.MkpError
import ru.otus.otuskotlin.mykotlin.common.models.MkpOp

sealed interface IDbOpResponse: IDbResponse<MkpOp>

data class DbOpResponseOk(
    val data: MkpOp
): IDbOpResponse

data class DbOpResponseErr(
    val errors: List<MkpError> = emptyList()
): IDbOpResponse {
    constructor(err: MkpError): this(listOf(err))
}

data class DbOpResponseErrWithData(
    val data: MkpOp,
    val errors: List<MkpError> = emptyList()
): IDbOpResponse {
    constructor(ad: MkpOp, err: MkpError): this(ad, listOf(err))
}