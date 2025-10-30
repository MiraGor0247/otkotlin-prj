package ru.otus.otuskotlin.mykotlin.business.stub.repo

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import ru.otus.otuskotlin.mykotlin.repo.tests.OpRepositoryMock
import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.MkpCorSettings
import ru.otus.otuskotlin.mykotlin.common.models.*
import ru.otus.otuskotlin.mykotlin.common.repo.DbOpResponseOk
import ru.otus.otuskotlin.mykotlin.common.repo.DbOpsResponseOk
import ru.otus.otuskotlin.mykotlin.kotlin.MkpOpProcessor

class RepoOrdersBusinessTests {
    private val userId = MkpUserId("321")
    private val command = MkpCommand.DELETE

    private val initOp = MkpOp(
        id = MkpOpId("123"),
        orderNum = "101",
        title = "abc",
        amount = MkpOpAmount(12000.0),
        ownerId = userId,
        opType = MkpPaidType.PAID,
        visibility = MkpVisibility.VISIBLE_PUBLIC,
    )

    private val orderOp = MkpOp(
        id = MkpOpId("122"),
        orderNum = "202",
        title = "asd",
        amount = MkpOpAmount(1200.0),
        opType = MkpPaidType.PAID,
        visibility = MkpVisibility.VISIBLE_PUBLIC,
    )
    private val repo = OpRepositoryMock(
        invokeReadOp = {
            DbOpResponseOk(
                data = initOp,
            )
        },
        invokeSearchOp = {
            DbOpsResponseOk(
                data = listOf(orderOp)
            )
        }
    )
    private val settings = MkpCorSettings(
        repoTest = repo
    )
    private val processor = MkpOpProcessor(settings)

    @Test
    fun repoOffersSuccessTest() = runTest {
        val ctx = MkpContext(
            command = command,
            state = MkpState.NONE,
            workMode = MkpWorkMode.TEST,
            opRequest = MkpOp(
                id = MkpOpId("123"),
            ),
        )
        processor.exec(ctx)
        assertEquals(MkpState.FINISHING, ctx.state)
        assertEquals(1, ctx.opsResponse.size)
        assertEquals(MkpPaidType.PAID, ctx.opsResponse.first().opType)
    }
    @Test
    fun repoOrdersNotFoundTest() = repoNotFoundTest(command)
}