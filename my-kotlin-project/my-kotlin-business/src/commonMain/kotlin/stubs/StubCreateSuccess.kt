package ru.otus.otuskotlin.mykotlin.kotlin.stubs

import ru.otus.otuskotlin.mykotlin.LogLevel
import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.MkpCorSettings
import ru.otus.otuskotlin.mykotlin.common.models.MkpOpAmount
import ru.otus.otuskotlin.mykotlin.common.models.MkpPaidType
import ru.otus.otuskotlin.mykotlin.common.models.MkpState
import ru.otus.otuskotlin.mykotlin.common.stubs.MkpStubs
import ru.otus.otuskotlin.mykotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.mykotlin.lib.cor.worker
import ru.otus.otuskotlin.mykotlin.stubs.MkpOpStub

fun ICorChainDsl<MkpContext>.stubCreateSuccess(title: String, corSettings: MkpCorSettings) = worker {
    this.title = title
    this.description = """
        Кейс успеха для создания заказов
    """.trimIndent()
    on { stubCase == MkpStubs.SUCCESS && state == MkpState.RUNNING }
    val logger = corSettings.loggerProvider.logger("stubOrdersSuccess")
    handle {
        logger.doWithLogging(id = this.requestId.asString(), LogLevel.DEBUG) {
            state = MkpState.FINISHING
            val stub = MkpOpStub.prepareResult {
                opRequest.title.takeIf { it.isNotBlank() }?.also { this.title = it }
                opRequest.orderNum.takeIf { it.isNotBlank() }?.also { this.orderNum = it }
                opRequest.opType.takeIf { it != MkpPaidType.NONE }?.also { this.opType = it }
                opRequest.amount.takeIf {it != MkpOpAmount.NONE }?.also {this.amount = it}
            }
            opResponse = stub
        }
    }
}