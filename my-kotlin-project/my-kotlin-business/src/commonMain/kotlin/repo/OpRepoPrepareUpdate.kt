package ru.otus.otuskotlin.mykotlin.kotlin.repo

import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.models.MkpState
import ru.otus.otuskotlin.mykotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.mykotlin.lib.cor.worker

fun ICorChainDsl<MkpContext>.repoPrepareUpdate(title: String) = worker {
    this.title = title
    description = "Готовим данные к сохранению в БД: совмещаем данные, прочитанные из БД, " +
            "и данные, полученные от пользователя"
    on { state == MkpState.RUNNING }
    handle {
        opRepoPrepare = opRepoRead.deepCopy().apply {
            this.title = opValidated.title
            orderNum = opValidated.orderNum
            opType = opValidated.opType
            visibility = opValidated.visibility
            lock = opValidated.lock
        }
    }
}