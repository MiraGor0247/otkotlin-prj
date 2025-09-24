package ru.otus.otuskotlin.mykotlin.kotlin.stubs

import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.MkpCorSettings
import ru.otus.otuskotlin.mykotlin.common.models.MkpState
import ru.otus.otuskotlin.mykotlin.common.stubs.MkpStubs
import ru.otus.otuskotlin.mykotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.mykotlin.lib.cor.worker
import ru.otus.otuskotlin.mykotlin.LogLevel
import ru.otus.otuskotlin.mykotlin.common.models.MkpPaidType
import ru.otus.otuskotlin.mykotlin.stubs.MkpOpStub

fun ICorChainDsl<MkpContext>.stubSearchSuccess(title: String, corSettings: MkpCorSettings) = worker {
    this.title = title
    this.description = """
        Кейс успеха для поиска заказов
    """.trimIndent()
    on { stubCase == MkpStubs.SUCCESS && state == MkpState.RUNNING }
    val logger = corSettings.loggerProvider.logger("stubOrdersSuccess")
    handle {
        logger.doWithLogging(id = this.requestId.asString(), LogLevel.DEBUG) {
            state = MkpState.FINISHING
            opsResponse.addAll(MkpOpStub.prepareSearchList(title, opFilterRequest.searchString, MkpPaidType.UNPAID))
        }
    }
}