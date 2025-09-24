package ru.otus.otuskotlin.mykotlin.business.stub.validation

import ru.otus.otuskotlin.mykotlin.common.models.MkpCommand
import kotlin.test.Test

class BusinessValidationCreateTest: BaseBusinessValidationTest() {
    override val command: MkpCommand = MkpCommand.CREATE

    @Test fun correctTitle() = validationTitleCorrect(command, processor)
    @Test fun emptyTitle() = validationTitleEmpty(command, processor)
    @Test fun badSymbolsTitle() = validationTitleSymbols(command, processor)

    @Test fun correctAmount() = validationAmountCorrect(command, processor)
    @Test fun emptyAmount() = validationAmountIsEmpty(command, processor)
}