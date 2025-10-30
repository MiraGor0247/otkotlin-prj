package ru.otus.otuskotlin.mykotlin.kotlin.repo

import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.helpers.fail
import ru.otus.otuskotlin.mykotlin.common.models.MkpState
import ru.otus.otuskotlin.mykotlin.common.repo.*
import ru.otus.otuskotlin.mykotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.mykotlin.lib.cor.worker

fun ICorChainDsl<MkpContext>.repoSearch(title: String) = worker {
    this.title = title
    description = "Поиск заказов в БД по фильтру"
    on { state == MkpState.RUNNING }
    handle {
        val request = DbOpFilterRequest(
            titleFilter = opFilterValidated.searchString,
            ownerId = opFilterValidated.userId,
            opType = opFilterValidated.paidType,
        )
        when(val result = opRepo.searchOp(request)) {
            is DbOpsResponseOk -> opsRepoDone = result.data.toMutableList()
            is DbOpsResponseErr -> fail(result.errors)
        }
    }
}