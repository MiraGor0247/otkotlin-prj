package ru.otus.otuskotlin.mykotlin.app.spring.repo

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import ru.otus.otuskotlin.mykotlin.common.repo.IRepoOp
import ru.otus.otuskotlin.mykotlin.repo.inmemory.OpRepoInMemory

@TestConfiguration
class RepoInMemoryConfig {
    @Suppress("unused")
    @Bean()
    @Primary
    fun prodRepo(): IRepoOp = OpRepoInMemory()
}