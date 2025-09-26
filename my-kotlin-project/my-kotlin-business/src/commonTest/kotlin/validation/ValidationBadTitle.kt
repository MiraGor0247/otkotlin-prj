package ru.otus.otuskotlin.mykotlin.business.stub.validation

import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.mykotlin.common.models.*
import ru.otus.otuskotlin.mykotlin.kotlin.MkpOpProcessor
import ru.otus.otuskotlin.mykotlin.common.MkpContext
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


fun validationTitleCorrect(command: MkpCommand, processor: MkpOpProcessor) = runTest {
    val ctx = MkpContext(
        command = command,
        state = MkpState.NONE,
        workMode = MkpWorkMode.TEST,
        opRequest = MkpOp(
            id = MkpOpId("42-24-aaa-BBB"),
            orderNum = "42",
            title = "abc",
            amount = MkpOpAmount(200.00),
            opType = MkpPaidType.PAID,
            visibility = MkpVisibility.VISIBLE_PUBLIC,
            lock = MkpOpLock("42-24-aaa-BBB"),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(MkpState.FAILING, ctx.state)
}

fun validationTitleEmpty(command: MkpCommand, processor: MkpOpProcessor) = runTest {
    val ctx = MkpContext(
        command = command,
        state = MkpState.NONE,
        workMode = MkpWorkMode.TEST,
        opRequest = MkpOp(
            id = MkpOpId("42-24-aaa-BBB"),
            orderNum = "42",
            title = "",
            amount = MkpOpAmount(200.00),
            opType = MkpPaidType.PAID,
            visibility = MkpVisibility.VISIBLE_PUBLIC,
            lock = MkpOpLock("42-24-aaa-BBB"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(MkpState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("title", error?.field)
    assertContains(error?.message ?: "", "title")
}

fun validationTitleSymbols(command: MkpCommand, processor: MkpOpProcessor) = runTest {
    val ctx = MkpContext(
        command = command,
        state = MkpState.NONE,
        workMode = MkpWorkMode.TEST,
        opRequest = MkpOp(
            id = MkpOpId("42-24-aaa-BBB"),
            orderNum = "42",
            title = "!@#$%^&*(),.{}",
            amount = MkpOpAmount(200.00),
            opType = MkpPaidType.PAID,
            visibility = MkpVisibility.VISIBLE_PUBLIC,
            lock = MkpOpLock("42-24-aaa-BBB"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(MkpState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("title", error?.field)
    assertContains(error?.message ?: "", "title")
}