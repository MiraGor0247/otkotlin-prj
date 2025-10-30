package ru.otus.otuskotlin.mykotlin.common.repo

import ru.otus.otuskotlin.mykotlin.common.models.MkpError
import ru.otus.otuskotlin.mykotlin.common.models.MkpOp

sealed interface IDbOpsResponse: IDbResponse<List<MkpOp>>

data class DbOpsResponseOk(
    val data: List<MkpOp>
): IDbOpsResponse

@Suppress("unused")
data class DbOpsResponseErr(
    val errors: List<MkpError> = emptyList()
): IDbOpsResponse {
    constructor(err: MkpError): this(listOf(err))
}