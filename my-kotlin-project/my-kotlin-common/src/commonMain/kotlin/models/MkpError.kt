package ru.otus.otuskotlin.mykotlin.common.models

data class MkpError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val message: String = "",
    val exception: Throwable? = null,
)
