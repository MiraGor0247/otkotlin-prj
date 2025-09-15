package ru.otus.otuskotlin.mykotlin

import kotlin.reflect.KClass
import kotlin.reflect.KFunction

class MkpLoggerProvider(
    private val provider: (String) -> IMkpLogWrapper = { IMkpLogWrapper.DEFAULT }
) {
    /**
     * Инициализирует и возвращает экземпляр логера
     */
    fun logger(loggerId: String): IMkpLogWrapper = provider(loggerId)

    /**
     * Инициализирует и возвращает экземпляр логера
     */
    fun logger(clazz: KClass<*>): IMkpLogWrapper = provider(clazz.qualifiedName ?: clazz.simpleName ?: "(unknown)")

    /**
     * Инициализирует и возвращает экземпляр логера
     */
    fun logger(function: KFunction<*>): IMkpLogWrapper = provider(function.name)
}

