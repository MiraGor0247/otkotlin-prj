package ru.otus.otuskotlin.mykotlin.kotlin

import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.MkpCorSettings
import ru.otus.otuskotlin.mykotlin.common.models.MkpState
import ru.otus.otuskotlin.mykotlin.common.models.MkpPaidType
import ru.otus.otuskotlin.mykotlin.stubs.MkpOpStub

@Suppress("unused", "RedundantSuspendModifier")
class MkpOpProcessor(val corSettings: MkpCorSettings) {

    suspend fun exec(ctx: MkpContext) {
        ctx.opResponse = MkpOpStub.get()
        ctx.opsResponse = MkpOpStub.prepareSearchList("order search", "001", MkpPaidType.PAID).toMutableList()
        ctx.state = MkpState.RUNNING
    }
}