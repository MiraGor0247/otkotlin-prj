package ru.otus.otuskotlin.mykotlin.kotlin.validation

import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.models.MkpState
import ru.otus.otuskotlin.mykotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.mykotlin.lib.cor.worker

fun ICorChainDsl<MkpContext>.finishAdValidation(title: String) = worker {
    this.title = title
    on { state == MkpState.RUNNING }
    handle {
        opValidated = opValidating
    }
}

fun ICorChainDsl<MkpContext>.finishAdFilterValidation(title: String) = worker {
    this.title = title
    on { state == MkpState.RUNNING }
    handle {
        opFilterValidated = opFilterValidating
    }
}