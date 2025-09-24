package ru.otus.otuskotlin.mykotlin.business.stub.validation

import ru.otus.otuskotlin.mykotlin.common.models.MkpCommand
import kotlin.test.Test

class BusinessValidationDeleteTest: BaseBusinessValidationTest() {
    override val command = MkpCommand.DELETE

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun trimId() = validationIdTrim(command, processor)
    @Test fun emptyId() = validationIdEmpty(command, processor)
    @Test fun badFormatId() = validationIdFormat(command, processor)

    @Test fun correctLock() = validationLockCorrect(command, processor)
    @Test fun trimLock() = validationLockTrim(command, processor)
    @Test fun emptyLock() = validationLockEmpty(command, processor)
    @Test fun badFormatLock() = validationLockFormat(command, processor)
}
