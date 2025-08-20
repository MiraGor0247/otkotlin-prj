package ru.otus.otuskotlin.mykotlin.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class MkpRequestId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = MkpRequestId("")
    }
}
