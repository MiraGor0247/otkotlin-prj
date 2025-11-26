package ru.otus.otuskotlin.mykotlin.business.validation

import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.models.MkpOp
import ru.otus.otuskotlin.mykotlin.common.models.MkpOpFilter
import ru.otus.otuskotlin.mykotlin.common.models.MkpState
import ru.otus.otuskotlin.mykotlin.kotlin.validation.validateSearchStringLength
import ru.otus.otuskotlin.mykotlin.lib.cor.rootChain
import kotlin.test.Test
import kotlin.test.assertEquals

class ValidateSearchStringLengthTest {
    @Test
    fun emptyString() = runTest {
        val ctx = MkpContext(state = MkpState.RUNNING, opFilterValidating = MkpOpFilter(searchString = ""))
        chain.exec(ctx)
        assertEquals(MkpState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
    }

    @Test
    fun blankString() = runTest {
        val ctx = MkpContext(state = MkpState.RUNNING, opFilterValidating = MkpOpFilter(searchString = "  "))
        chain.exec(ctx)
        assertEquals(MkpState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
    }

    @Test
    fun shortString() = runTest {
        val ctx = MkpContext(state = MkpState.RUNNING, opFilterValidating = MkpOpFilter(searchString = "12"))
        chain.exec(ctx)
        assertEquals(MkpState.FAILING, ctx.state)
        assertEquals(1, ctx.errors.size)
        assertEquals("validation-searchString-tooShort", ctx.errors.first().code)
    }

    @Test
    fun normalString() = runTest {
        val ctx = MkpContext(state = MkpState.RUNNING, opFilterValidating = MkpOpFilter(searchString = "123"))
        chain.exec(ctx)
        assertEquals(MkpState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
    }

    @Test
    fun longString() = runTest {
        val ctx = MkpContext(state = MkpState.RUNNING, opFilterValidating = MkpOpFilter(searchString = "12".repeat(51)))
        chain.exec(ctx)
        assertEquals(MkpState.FAILING, ctx.state)
        assertEquals(1, ctx.errors.size)
        assertEquals("validation-searchString-tooLong", ctx.errors.first().code)
    }

    companion object {
        val chain = rootChain {
            validateSearchStringLength("")
        }.build()
    }
}