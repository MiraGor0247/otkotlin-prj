package ru.otus.otuskotlin.mykotlin.business.stub.stub

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail
import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.models.*
import ru.otus.otuskotlin.mykotlin.common.stubs.MkpStubs
import ru.otus.otuskotlin.mykotlin.kotlin.MkpOpProcessor
import ru.otus.otuskotlin.mykotlin.stubs.MkpOpStub

class OpSearchStubTest {
    private val processor = MkpOpProcessor()
    val filter = MkpOpFilter(searchString = "Kia Rio")

    @Test
    fun read() = runTest {
        val ctx = MkpContext(
            command = MkpCommand.SEARCH,
            state = MkpState.NONE,
            workMode = MkpWorkMode.STUB,
            stubCase = MkpStubs.SUCCESS,
            opFilterRequest = filter,
        )
        processor.exec(ctx)
        assertTrue(ctx.opsResponse.size > 1)
        val first = ctx.opsResponse.firstOrNull() ?: fail("Empty response list")
        assertTrue(first.title.contains(filter.searchString))
        assertTrue(first.orderNum.contains(filter.searchString))
        with (MkpOpStub.get()) {
            assertEquals(opType, first.opType)
            assertEquals(visibility, first.visibility)
        }

        @Test
        fun badId() = runTest {
            val ctx =  MkpContext(
                command = MkpCommand.SEARCH,
                state = MkpState.NONE,
                workMode = MkpWorkMode.STUB,
                stubCase = MkpStubs.BAD_ID,
                opFilterRequest = filter,
            )
            processor.exec(ctx)
            assertEquals(MkpOp(), ctx.opResponse)
            assertEquals("id", ctx.errors.firstOrNull()?.field)
            assertEquals("validation", ctx.errors.firstOrNull()?.group)
        }

        @Test
        fun databaseError() = runTest {
            val ctx = MkpContext(
                command = MkpCommand.SEARCH,
                state = MkpState.NONE,
                workMode = MkpWorkMode.STUB,
                stubCase = MkpStubs.BAD_ID,
                opFilterRequest = filter,
            )
            processor.exec(ctx)
            assertEquals(MkpOp(), ctx.opResponse)
            assertEquals("internal", ctx.errors.firstOrNull()?.group)
        }
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = MkpContext(
            command = MkpCommand.SEARCH,
            state = MkpState.NONE,
            workMode = MkpWorkMode.STUB,
            stubCase = MkpStubs.NONE,
            opFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(MkpOp(), ctx.opResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}