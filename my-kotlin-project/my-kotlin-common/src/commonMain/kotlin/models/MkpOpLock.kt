package ru.otus.otuskotlin.mykotlin.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class MkpOpLock(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = MkpOpLock("")
    }
}
