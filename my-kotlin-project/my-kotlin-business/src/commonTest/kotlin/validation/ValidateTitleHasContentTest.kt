package ru.otus.otuskotlin.mykotlin.business.validation

import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.models.MkpOp
import ru.otus.otuskotlin.mykotlin.common.models.MkpOpFilter
import ru.otus.otuskotlin.mykotlin.common.models.MkpState
import ru.otus.otuskotlin.mykotlin.kotlin.validation.validateTitleHasContent
import ru.otus.otuskotlin.mykotlin.lib.cor.rootChain
import kotlin.test.Test
import kotlin.test.assertEquals

class ValidateTitleHasContentTest  {
    @Test
    fun emptyString() = runTest {
        val ctx = MkpContext(state = MkpState.RUNNING, opValidating = MkpOp(title = ""))
        chain.exec(ctx)
        assertEquals(MkpState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
    }

    @Test
    fun noContent() = runTest {
        val ctx = MkpContext(state = MkpState.RUNNING, opValidating = MkpOp(title = "12!@#$%^&*()_+-="))
        chain.exec(ctx)
        assertEquals(MkpState.FAILING, ctx.state)
        assertEquals(1, ctx.errors.size)
        assertEquals("validation-title-noContent", ctx.errors.first().code)
    }

    @Test
    fun normalString() = runTest {
        val ctx = MkpContext(state = MkpState.RUNNING, opFilterValidating = MkpOpFilter(searchString = "Ж"))
        chain.exec(ctx)
        assertEquals(MkpState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
    }

    companion object {
        val chain = rootChain {
            validateTitleHasContent("")
        }.build()
    }
}