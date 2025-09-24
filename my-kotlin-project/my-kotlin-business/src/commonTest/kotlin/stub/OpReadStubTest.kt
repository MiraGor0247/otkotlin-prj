package ru.otus.otuskotlin.mykotlin.business.stub.stub

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.models.*
import ru.otus.otuskotlin.mykotlin.kotlin.MkpOpProcessor
import ru.otus.otuskotlin.mykotlin.common.stubs.MkpStubs
import ru.otus.otuskotlin.mykotlin.stubs.MkpOpStub

class OpReadStubTest {
    private val processor = MkpOpProcessor()
    val id = MkpOpId("111")

    @Test
    fun read() = runTest {

        val ctx = MkpContext(
            command = MkpCommand.READ,
            state = MkpState.NONE,
            workMode = MkpWorkMode.STUB,
            stubCase = MkpStubs.SUCCESS,
            opRequest = MkpOp(
                id = id,
            ),
        )
        processor.exec(ctx)
        with (MkpOpStub.get()) {
            assertEquals(id, ctx.opResponse.id)
            assertEquals(title, ctx.opResponse.title)
            assertEquals(opType, ctx.opResponse.opType)
            assertEquals(visibility, ctx.opResponse.visibility)
        }
    }

    @Test
    fun badId() = runTest {
        val ctx = MkpContext(
            command = MkpCommand.READ,
            state = MkpState.NONE,
            workMode = MkpWorkMode.STUB,
            stubCase = MkpStubs.BAD_ID,
            opRequest = MkpOp(),
        )
        processor.exec(ctx)
        assertEquals(MkpOp(), ctx.opResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
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
            ),
        )
        processor.exec(ctx)
        assertEquals(MkpOp(), ctx.opResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}