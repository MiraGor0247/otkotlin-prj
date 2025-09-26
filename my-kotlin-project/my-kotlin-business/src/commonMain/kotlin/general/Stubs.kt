package ru.otus.otuskotlin.mykotlin.kotlin.general

import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.models.MkpState
import ru.otus.otuskotlin.mykotlin.common.models.MkpWorkMode
import ru.otus.otuskotlin.mykotlin.kotlin.stubs.stubNoCase
import ru.otus.otuskotlin.mykotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.mykotlin.lib.cor.chain

fun ICorChainDsl<MkpContext>.stubs(title: String, block: ICorChainDsl<MkpContext>.() -> Unit) = chain {
    block()
    stubNoCase("Ошибка: запрошенный стаб недопустим")
    this.title = title
    on { workMode == MkpWorkMode.STUB && state == MkpState.RUNNING }
}