package ru.otus.otuskotlin.mykotlin.kotlin.stubs

import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.models.MkpState
import ru.otus.otuskotlin.mykotlin.common.models.MkpWorkMode
import ru.otus.otuskotlin.mykotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.mykotlin.lib.cor.chain

fun ICorChainDsl<MkpContext>.stubs(title: String, block: ICorChainDsl<MkpContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == MkpWorkMode.STUB && state == MkpState.RUNNING }
}