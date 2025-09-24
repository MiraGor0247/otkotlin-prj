package ru.otus.otuskotlin.mykotlin.kotlin.general

import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.models.MkpCommand
import ru.otus.otuskotlin.mykotlin.common.models.MkpState
import ru.otus.otuskotlin.mykotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.mykotlin.lib.cor.chain

fun ICorChainDsl<MkpContext>.operation(
    title: String,
    command: MkpCommand,
    block: ICorChainDsl<MkpContext>.() -> Unit
) = chain {
    block()
    this.title = title
    on { this.command == command && state == MkpState.RUNNING }
}