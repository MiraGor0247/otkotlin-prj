package ru.otus.otuskotlin.mykotlin.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.otus.otuskotlin.mykotlin.MkpLoggerProvider
import ru.otus.otuskotlin.mykotlin.base.MkpAppSettings
import ru.otus.otuskotlin.mykotlin.base.SpringWsSessionRepo
import ru.otus.otuskotlin.mykotlin.common.MkpCorSettings
import ru.otus.otuskotlin.mykotlin.kotlin.MkpOpProcessor
import ru.otus.otuskotlin.mykotlin.mkpLoggerLogback

@Suppress("unused")
@Configuration
class OpConfig {
    @Bean
    fun processor(corSettings: MkpCorSettings) = MkpOpProcessor(corSettings = corSettings)

    @Bean
    fun loggerProvider(): MkpLoggerProvider = MkpLoggerProvider{ mkpLoggerLogback(it) }

    @Bean
    fun corSettings(): MkpCorSettings = MkpCorSettings(
        loggerProvider = loggerProvider(),
        wsSessions = wsRepo(),
    )

    @Bean
    fun appSettings(
        corSettings: MkpCorSettings,
        processor: MkpOpProcessor,
    ) = MkpAppSettings(
        corSettings = corSettings,
        processor = processor,
    )

    @Bean
    fun wsRepo(): SpringWsSessionRepo = SpringWsSessionRepo()
}