package ru.otus.otuskotlin.mykotlin.app.spring.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.otus.otuskotlin.mykotlin.MkpLoggerProvider
import ru.otus.otuskotlin.mykotlin.app.spring.base.MkpAppSettings
import ru.otus.otuskotlin.mykotlin.app.spring.base.SpringWsSessionRepo
import ru.otus.otuskotlin.mykotlin.common.MkpCorSettings
import ru.otus.otuskotlin.mykotlin.common.repo.IRepoOp
import ru.otus.otuskotlin.mykotlin.kotlin.MkpOpProcessor
import ru.otus.otuskotlin.mykotlin.mkpLoggerLogback
import ru.otus.otuskotlin.mykotlin.repo.inmemory.OpRepoInMemory
import ru.otus.otuskotlin.mykotlin.repo.pg.RepoOpSql
import ru.otus.otuskotlin.mykotlin.repo.stubs.OpRepoStub

@Suppress("unused")
@EnableConfigurationProperties(OpConfigPostgres::class)
@Configuration
class OpConfig(val postgresConfig: OpConfigPostgres) {
    val logger: Logger = LoggerFactory.getLogger(OpConfig::class.java)
    @Bean
    fun processor(corSettings: MkpCorSettings) = MkpOpProcessor(corSettings = corSettings)

    @Bean
    fun loggerProvider(): MkpLoggerProvider = MkpLoggerProvider{ mkpLoggerLogback(it) }

    @Bean
    fun testRepo(): IRepoOp = OpRepoInMemory()

    @Bean
    fun prodRepo(): IRepoOp = RepoOpSql(postgresConfig.psql).apply {
        logger.info("Connecting to DB with ${this}")
    }

    @Bean
    fun stubRepo(): IRepoOp = OpRepoStub()

    @Bean
    fun corSettings(): MkpCorSettings = MkpCorSettings(
        loggerProvider = loggerProvider(),
        wsSessions = wsRepo(),
        repoTest = testRepo(),
        repoProd = prodRepo(),
        repoStub = stubRepo(),
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