package ru.otus.otuskotlin.mykotlin.business.stub.validation

import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.models.*
import ru.otus.otuskotlin.mykotlin.kotlin.MkpOpProcessor
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


fun validationAmountCorrect(command: MkpCommand, processor: MkpOpProcessor) = runTest {
    val ctx = MkpContext(
        command = command,
        state = MkpState.NONE,
        workMode = MkpWorkMode.TEST,
        opRequest = MkpOp(
            id = MkpOpId("42-24-aaa-BBB"),
            orderNum = "42",
            title = "abc",
            amount = MkpOpAmount(200.0),
            opType = MkpPaidType.PAID,
            visibility = MkpVisibility.VISIBLE_PUBLIC,
            lock = MkpOpLock("42-24-aaa-BBB"),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(MkpState.FAILING, ctx.state)
}

fun validationAmountIsEmpty(command: MkpCommand, processor: MkpOpProcessor) = runTest {
    val ctx = MkpContext(
        command = command,
        state = MkpState.NONE,
        workMode = MkpWorkMode.TEST,
        opRequest = MkpOp(
            id = MkpOpId("42-24-aaa-BBB"),
            orderNum = "42",
            title = "abc",
            amount = MkpOpAmount(Double.NaN),
            opType = MkpPaidType.PAID,
            visibility = MkpVisibility.VISIBLE_PUBLIC,
            lock = MkpOpLock("42-24-aaa-BBB"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(MkpState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("amount", error?.field)
    assertContains(error?.message ?: "", "amount")
}