package ru.otus.otuskotlin.mykotlin.kotlin.stubs

import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.models.MkpError
import ru.otus.otuskotlin.mykotlin.common.models.MkpState
import ru.otus.otuskotlin.mykotlin.common.stubs.MkpStubs
import ru.otus.otuskotlin.mykotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.mykotlin.lib.cor.worker
import ru.otus.otuskotlin.mykotlin.common.helpers.fail

fun ICorChainDsl<MkpContext>.stubValidationBadAmount(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки валидации для суммы заказа
    """.trimIndent()
    on { stubCase == MkpStubs.BAD_AMOUNT && state == MkpState.RUNNING }
    handle {
        fail(
            MkpError(
                group = "validation",
                code = "validation-amount",
                field = "amount",
                message = "Wrong amount field"
            )
        )
    }
}