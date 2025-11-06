package ru.otus.otuskotlin.mykotlin.kotlin.repo

import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.models.MkpState
import ru.otus.otuskotlin.mykotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.mykotlin.lib.cor.worker

fun ICorChainDsl<MkpContext>.repoPrepareDelete(title: String) = worker {
    this.title = title
    description = """
        Готовим данные к удалению из БД
    """.trimIndent()
    on { state == MkpState.RUNNING }
    handle {
        opRepoPrepare = opValidated.deepCopy()
    }
}