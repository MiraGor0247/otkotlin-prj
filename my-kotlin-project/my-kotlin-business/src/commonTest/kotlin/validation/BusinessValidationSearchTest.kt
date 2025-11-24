package ru.otus.otuskotlin.mykotlin.business.validation

import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.models.MkpCommand
import ru.otus.otuskotlin.mykotlin.common.models.MkpOpFilter
import ru.otus.otuskotlin.mykotlin.common.models.MkpState
import ru.otus.otuskotlin.mykotlin.common.models.MkpWorkMode
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


class BusinessValidationSearchTest: BaseBusinessValidationTest() {
    override val command = MkpCommand.SEARCH

    @Test
    fun correctEmpty() = runTest {
        val ctx = MkpContext(
            command = command,
            state = MkpState.NONE,
            workMode = MkpWorkMode.TEST,
            opFilterRequest = MkpOpFilter()
        )
        processor.exec(ctx)
        assertEquals(0, ctx.errors.size)
        assertNotEquals(MkpState.FAILING, ctx.state)
    }
}