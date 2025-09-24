package ru.otus.otuskotlin.mykotlin.kotlin.stubs

import ru.otus.otuskotlin.mykotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.mykotlin.lib.cor.worker
import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.helpers.fail
import ru.otus.otuskotlin.mykotlin.common.models.MkpState
import ru.otus.otuskotlin.mykotlin.common.models.MkpError

fun ICorChainDsl<MkpContext>.stubNoCase(title: String) = worker {
    this.title = title
    this.description = """
        Валидируем ситуацию, когда запрошен кейс, который не поддерживается в стабах
    """.trimIndent()
    on { state == MkpState.RUNNING }
    handle {
        fail(
            MkpError(
                code = "validation",
                field = "stub",
                group = "validation",
                message = "Wrong stub case is requested: ${stubCase.name}"
            )
        )
    }
}