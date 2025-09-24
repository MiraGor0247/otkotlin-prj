package ru.otus.otuskotlin.mykotlin.business.stub.validation

import ru.otus.otuskotlin.mykotlin.common.models.MkpCommand
import kotlin.test.Test

class BusinessValidationReadTest: BaseBusinessValidationTest() {
    override val command = MkpCommand.READ

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun trimId() = validationIdTrim(command, processor)
    @Test fun emptyId() = validationIdEmpty(command, processor)
    @Test fun badFormatId() = validationIdFormat(command, processor)
}