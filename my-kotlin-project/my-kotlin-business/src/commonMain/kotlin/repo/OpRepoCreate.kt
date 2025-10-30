package ru.otus.otuskotlin.mykotlin.kotlin.repo

import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.helpers.fail
import ru.otus.otuskotlin.mykotlin.common.models.MkpState
import ru.otus.otuskotlin.mykotlin.common.repo.DbOpRequest
import ru.otus.otuskotlin.mykotlin.common.repo.DbOpResponseErr
import ru.otus.otuskotlin.mykotlin.common.repo.DbOpResponseErrWithData
import ru.otus.otuskotlin.mykotlin.common.repo.DbOpResponseOk
import ru.otus.otuskotlin.mykotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.mykotlin.lib.cor.worker

fun ICorChainDsl<MkpContext>.repoCreate(title: String) = worker {
    this.title = title
    description = "Добавление заказа в БД"
    on { state == MkpState.RUNNING }
    handle {
        val request = DbOpRequest(opRepoPrepare)
        when(val result = opRepo.createOp(request)) {
            is DbOpResponseOk -> opRepoDone = result.data
            is DbOpResponseErr -> fail(result.errors)
            is DbOpResponseErrWithData -> fail(result.errors)
        }
    }
}