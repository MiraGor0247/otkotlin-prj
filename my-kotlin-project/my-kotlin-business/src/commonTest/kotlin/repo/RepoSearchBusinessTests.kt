package ru.otus.otuskotlin.mykotlin.business.repo

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import ru.otus.otuskotlin.mykotlin.repo.tests.OpRepositoryMock
import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.MkpCorSettings
import ru.otus.otuskotlin.mykotlin.common.models.*
import ru.otus.otuskotlin.mykotlin.common.repo.DbOpsResponseOk
import ru.otus.otuskotlin.mykotlin.kotlin.MkpOpProcessor

class RepoSearchBusinessTests {
    private val userId = MkpUserId("321")
    private val command = MkpCommand.SEARCH

    private val initOp = MkpOp(
        id = MkpOpId("123"),
        orderNum = "101",
        title = "abc",
        amount = MkpOpAmount(12000.0),
        ownerId = userId,
        opType = MkpPaidType.PAID,
        visibility = MkpVisibility.VISIBLE_PUBLIC,
    )
    private val repo = OpRepositoryMock(
        invokeSearchOp = {
            DbOpsResponseOk(
                data = listOf(initOp),
            )
        }
    )
    private val settings = MkpCorSettings(repoTest = repo)
    private val processor = MkpOpProcessor(settings)

    @Test
    fun repoSearchSuccessTest() = runTest {
        val ctx = MkpContext(
            command = command,
            state = MkpState.NONE,
            workMode = MkpWorkMode.TEST,
            opFilterRequest = MkpOpFilter(
                searchString = "abc",
                userId = userId,
                paidType = MkpPaidType.PAID
            ),
        )
        processor.exec(ctx)
        assertEquals(MkpState.FINISHING, ctx.state)
        assertEquals(1, ctx.opsResponse.size)
    }
}