package ru.otus.otuskotlin.mykotlin.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class MkpOpAmount(private val am: Double) {
    fun asDouble() = am

    companion object {
        val NONE = MkpOpAmount(0.0)
    }
}
