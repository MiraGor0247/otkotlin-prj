package ru.otus.otuskotlin.mykotlin.kotlin.validation

import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.helpers.errorValidation
import ru.otus.otuskotlin.mykotlin.common.helpers.fail
import ru.otus.otuskotlin.mykotlin.common.models.MkpOpAmount
import ru.otus.otuskotlin.mykotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.mykotlin.lib.cor.worker

fun ICorChainDsl<MkpContext>.validateAmountNotEmpty(title: String) = worker {
    this.title = title
    on { opValidating.amount == MkpOpAmount(Double.NaN) }
    handle {
        fail(
            errorValidation(
                field = "amount",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}