package ru.otus.otuskotlin.mykotlin.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class MkpPaymentId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = MkpPaymentId("")
    }
}
