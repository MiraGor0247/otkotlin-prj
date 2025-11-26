package ru.otus.otuskotlin.mykotlin.business.stub

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.models.*
import ru.otus.otuskotlin.mykotlin.kotlin.MkpOpProcessor
import ru.otus.otuskotlin.mykotlin.common.stubs.MkpStubs
import ru.otus.otuskotlin.mykotlin.stubs.MkpOpStub

class OpCreateStubTest {
    private val processor = MkpOpProcessor()
    val id = MkpOpId("111")
    val num = "orderNum 101"
    val title = "title 1111"
    val amount = MkpOpAmount(12000.0)
    val paid = MkpPaidType.PAID
    val visibility = MkpVisibility.VISIBLE_PUBLIC

    @Test
    fun create() = runTest {
        val ctx = MkpContext(
            command = MkpCommand.CREATE,
            state = MkpState.NONE,
            workMode = MkpWorkMode.STUB,
            stubCase = MkpStubs.SUCCESS,
            opRequest = MkpOp(
                id = id,
                orderNum = num,
                title = title,
                opType = paid,
                amount = amount,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(MkpOpStub.get().id, ctx.opResponse.id)
        assertEquals(title, ctx.opResponse.title)
        assertEquals(paid, ctx.opResponse.opType)
        assertEquals(amount, ctx.opResponse.amount)
        assertEquals(visibility, ctx.opResponse.visibility)
    }

    @Test
    fun badTitle() = runTest {
        val ctx = MkpContext(
            command = MkpCommand.CREATE,
            state = MkpState.NONE,
            workMode = MkpWorkMode.STUB,
            stubCase = MkpStubs.BAD_TITLE,
            opRequest = MkpOp(
                id = id,
                orderNum = num,
                title = "",
                opType = paid,
                amount = amount,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(MkpOp(), ctx.opResponse)
        assertEquals("title", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badAmount() = runTest {
        val ctx = MkpContext(
            command = MkpCommand.CREATE,
            state = MkpState.NONE,
            workMode = MkpWorkMode.STUB,
            stubCase = MkpStubs.BAD_AMOUNT,
            opRequest = MkpOp(
                id = id,
                orderNum = num,
                title = title,
                opType = paid,
                amount = MkpOpAmount(-1.00),
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(MkpOp(), ctx.opResponse)
        assertEquals("amount", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = MkpContext(
            command = MkpCommand.CREATE,
            state = MkpState.NONE,
            workMode = MkpWorkMode.STUB,
            stubCase = MkpStubs.DB_ERROR,
            opRequest = MkpOp(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(MkpOp(), ctx.opResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = MkpContext(
            command = MkpCommand.CREATE,
            state = MkpState.NONE,
            workMode = MkpWorkMode.STUB,
            stubCase = MkpStubs.NONE,
            opRequest = MkpOp(
                id = id,
                orderNum = num,
                title = title,
                opType = paid,
                amount = MkpOpAmount.NONE,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(MkpOp(), ctx.opResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}