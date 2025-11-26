package ru.otus.otuskotlin.mykotlin.business.validation

import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.models.*
import ru.otus.otuskotlin.mykotlin.kotlin.MkpOpProcessor
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


fun validationLockCorrect(command: MkpCommand, processor: MkpOpProcessor) = runTest {
    val ctx = MkpContext(
        command = command,
        state = MkpState.NONE,
        workMode = MkpWorkMode.TEST,
        opRequest = MkpOp(
            id = MkpOpId("111"),
            orderNum = "42",
            title = "abc",
            amount = MkpOpAmount(200.00),
            opType = MkpPaidType.PAID,
            visibility = MkpVisibility.VISIBLE_PUBLIC,
            lock = MkpOpLock("001"),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(MkpState.FAILING, ctx.state)
}

fun validationLockTrim(command: MkpCommand, processor: MkpOpProcessor) = runTest {
    val ctx = MkpContext(
        command = command,
        state = MkpState.NONE,
        workMode = MkpWorkMode.TEST,
        opRequest = MkpOp(
            id = MkpOpId("111"),
            orderNum = "42",
            title = "abc",
            amount = MkpOpAmount(200.00),
            opType = MkpPaidType.PAID,
            visibility = MkpVisibility.VISIBLE_PUBLIC,
            lock = MkpOpLock(" \n\t 001 \n\t "),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(MkpState.FAILING, ctx.state)
}

fun validationLockEmpty(command: MkpCommand, processor: MkpOpProcessor) = runTest {
    val ctx = MkpContext(
        command = command,
        state = MkpState.NONE,
        workMode = MkpWorkMode.TEST,
        opRequest = MkpOp(
            id = MkpOpId("111"),
            orderNum = "42",
            title = "abc",
            amount = MkpOpAmount(200.00),
            opType = MkpPaidType.PAID,
            visibility = MkpVisibility.VISIBLE_PUBLIC,
            lock = MkpOpLock(""),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(MkpState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("lock", error?.field)
    assertContains(error?.message ?: "", "id")
}

fun validationLockFormat(command: MkpCommand, processor: MkpOpProcessor) = runTest {
    val ctx = MkpContext(
        command = command,
        state = MkpState.NONE,
        workMode = MkpWorkMode.TEST,
        opRequest = MkpOp(
            id = MkpOpId("111"),
            orderNum = "42",
            title = "abc",
            amount = MkpOpAmount(200.00),
            opType = MkpPaidType.PAID,
            visibility = MkpVisibility.VISIBLE_PUBLIC,
            lock = MkpOpLock("!@#\$%^&*(),.{}"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(MkpState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("lock", error?.field)
    assertContains(error?.message ?: "", "id")
}



