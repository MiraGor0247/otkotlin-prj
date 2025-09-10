package ru.otus.otuskotlin.mykotlin

import ch.qos.logback.classic.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass
import ru.otus.otuskotlin.mykotlin.IMkpLogWrapper

/**
 * Generate internal MpLogContext logger
 *
 * @param logger Logback instance from [LoggerFactory.getLogger()]
 */
fun mkpLoggerLogback(logger: Logger): IMkpLogWrapper = MkpLogWrapperLogback(
    logger = logger,
    loggerId = logger.name,
)

fun mkpLoggerLogback(clazz: KClass<*>): IMkpLogWrapper = mkpLoggerLogback(LoggerFactory.getLogger(clazz.java) as Logger)
@Suppress("unused")
fun mkpLoggerLogback(loggerId: String): IMkpLogWrapper = mkpLoggerLogback(LoggerFactory.getLogger(loggerId) as Logger)
