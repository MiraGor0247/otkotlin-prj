package ru.otus.otuskotlin.mykotlin.business.validation

import ru.otus.otuskotlin.mykotlin.common.MkpCorSettings
import ru.otus.otuskotlin.mykotlin.common.models.MkpCommand
import ru.otus.otuskotlin.mykotlin.kotlin.MkpOpProcessor
import ru.otus.otuskotlin.mykotlin.repo.common.OpRepoInitialized
import ru.otus.otuskotlin.mykotlin.repo.inmemory.OpRepoInMemory
import ru.otus.otuskotlin.mykotlin.stubs.MkpOpStub

abstract class BaseBusinessValidationTest {
    protected abstract val command: MkpCommand
    private val repo = OpRepoInitialized(
        repo = OpRepoInMemory(),
        initObjects = listOf(
            MkpOpStub.get(),
        ),
    )
    private val settings by lazy { MkpCorSettings(repoTest = repo) }
    protected val processor by lazy { MkpOpProcessor(settings) }
}