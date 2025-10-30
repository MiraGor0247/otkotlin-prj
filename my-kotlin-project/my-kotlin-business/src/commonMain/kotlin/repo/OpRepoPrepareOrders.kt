package ru.otus.otuskotlin.mykotlin.kotlin.repo

import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.models.MkpState
import ru.otus.otuskotlin.mykotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.mykotlin.lib.cor.worker

fun ICorChainDsl<MkpContext>.repoPrepareOrders(title: String) = worker {
    this.title = title
    description = "Готовим данные к поиску заказов в БД"
    on { state == MkpState.RUNNING }
    handle {
        opRepoPrepare = opRepoRead.deepCopy()
        opRepoDone = opRepoRead.deepCopy()
    }
}