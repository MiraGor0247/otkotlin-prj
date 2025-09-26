package ru.otus.otuskotlin.mykotlin.business.stub.validation

import ru.otus.otuskotlin.mykotlin.common.MkpCorSettings
import ru.otus.otuskotlin.mykotlin.common.models.MkpCommand
import ru.otus.otuskotlin.mykotlin.kotlin.MkpOpProcessor

abstract class BaseBusinessValidationTest {
    protected abstract val command: MkpCommand
    private val settings by lazy { MkpCorSettings() }
    protected val processor by lazy { MkpOpProcessor(settings) }
}