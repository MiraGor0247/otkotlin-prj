package ru.otus.otuskotlin.mykotlin.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class MkplOpAmount(private val am: Double) {
    fun asDouble() = am

    companion object {
        val NONE = MkplOpAmount(0.0)
    }
}
