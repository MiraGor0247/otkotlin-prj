package ru.otus.otuskotlin.mykotlin.kotlin.validation

import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.models.MkpState
import ru.otus.otuskotlin.mykotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.mykotlin.lib.cor.chain

fun ICorChainDsl<MkpContext>.validation(block: ICorChainDsl<MkpContext>.() -> Unit) = chain {
    block()
    title = "Валидация"

    on { state == MkpState.RUNNING }
}
