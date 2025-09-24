package ru.otus.otuskotlin.mykotlin.common.models

import ru.otus.otuskotlin.mykotlin.LogLevel

data class MkpError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val message: String = "",
    val exception: Throwable? = null,
    val level: LogLevel = LogLevel.ERROR,
)
