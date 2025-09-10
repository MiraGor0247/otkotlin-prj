package ru.otus.otuskotlin.mykotlin.common.logs

import ru.otus.otuskotlin.mykotlin.common.models.MkpError

fun Throwable.asMkpError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = MkpError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)