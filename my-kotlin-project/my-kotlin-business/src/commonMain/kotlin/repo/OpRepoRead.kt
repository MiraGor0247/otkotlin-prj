package ru.otus.otuskotlin.mykotlin.kotlin.repo

import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.helpers.fail
import ru.otus.otuskotlin.mykotlin.common.models.MkpState
import ru.otus.otuskotlin.mykotlin.common.repo.DbOpIdRequest
import ru.otus.otuskotlin.mykotlin.common.repo.DbOpResponseErr
import ru.otus.otuskotlin.mykotlin.common.repo.DbOpResponseErrWithData
import ru.otus.otuskotlin.mykotlin.common.repo.DbOpResponseOk
import ru.otus.otuskotlin.mykotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.mykotlin.lib.cor.worker

fun ICorChainDsl<MkpContext>.repoRead(title: String) = worker {
    this.title = title
    description = "Чтение объявления из БД"
    on { state == MkpState.RUNNING }
    handle {
        val request = DbOpIdRequest(opValidated)
        when(val result = opRepo.readOp(request)) {
            is DbOpResponseOk -> opRepoRead = result.data
            is DbOpResponseErr -> fail(result.errors)
            is DbOpResponseErrWithData -> {
                fail(result.errors)
                opRepoRead = result.data
            }
        }
    }
}