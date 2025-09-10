package ru.otus.otuskotlin.mykotlin.rabbit

import kotlinx.coroutines.runBlocking
import ru.otus.otuskotlin.mykotlin.MkpLoggerProvider
import ru.otus.otuskotlin.mykotlin.common.MkpCorSettings
import ru.otus.otuskotlin.mykotlin.mkpLoggerLogback
import ru.otus.otuskotlin.mykotlin.rabbit.config.MkpAppSettings
import ru.otus.otuskotlin.mykotlin.rabbit.config.RabbitConfig
import ru.otus.otuskotlin.mykotlin.rabbit.mappers.fromArgs

fun main(vararg args: String) = runBlocking {
    val appSettings = MkpAppSettings(
        rabbit = RabbitConfig.fromArgs(*args),
        corSettings = MkpCorSettings(
            loggerProvider = MkpLoggerProvider { mkpLoggerLogback(it) }
        )
    )
    val app = RabbitApp(appSettings = appSettings, this)
    app.start()
}