package ru.otus.otuskotlin.mykotlin.kotlin.repo

import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.models.MkpState
import ru.otus.otuskotlin.mykotlin.common.models.MkpUserId
import ru.otus.otuskotlin.mykotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.mykotlin.lib.cor.worker

fun ICorChainDsl<MkpContext>.repoPrepareCreate(title: String) = worker {
    this.title = title
    description = "Подготовка объекта к сохранению в базе данных"
    on { state == MkpState.RUNNING }
    handle {
        opRepoPrepare = opValidated.deepCopy()
        // TODO будет реализовано в занятии по управлению пользвателями
        opRepoPrepare.ownerId = MkpUserId.NONE
    }
}