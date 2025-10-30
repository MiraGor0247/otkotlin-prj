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
import ru.otus.otuskotlin.mykotlin.kotlin.MkpOpProcessor

class RepoCreateBusinessTests {
    private val userId = MkpUserId("321")
    private val command = MkpCommand.CREATE
    private val uuid = "10000000-0000-0000-0000-000000000001"

    private val repo = OpRepositoryMock(
        invokeCreateOp = {
            DbOpResponseOk(
                data = MkpOp(
                    id = MkpOpId(uuid),
                    orderNum = it.op.orderNum,
                    title = it.op.title,
                    ownerId = userId,
                    amount = it.op.amount,
                    opType = it.op.opType,
                    visibility = it.op.visibility,
                )
            )
        }
    )
    private val settings = MkpCorSettings(
        repoTest = repo
    )
    private val processor = MkpOpProcessor(settings)

    @Test
    fun repoCreateSuccessTest() = runTest {
        val ctx = MkpContext(
            command = command,
            state = MkpState.NONE,
            workMode = MkpWorkMode.TEST,
            opRequest = MkpOp(
                id = MkpOpId("123"),
                orderNum = "101",
                title = "abc",
                amount = MkpOpAmount(12000.0),
                opType = MkpPaidType.PAID,
                visibility = MkpVisibility.VISIBLE_PUBLIC,
            ),
        )
        processor.exec(ctx)
        assertEquals(MkpState.FINISHING, ctx.state)
        assertNotEquals(MkpOpId.NONE, ctx.opResponse.id)
        assertEquals("abc", ctx.opResponse.title)
        assertEquals("101", ctx.opResponse.orderNum)
        assertEquals(MkpPaidType.PAID, ctx.opResponse.opType)
        assertEquals(MkpVisibility.VISIBLE_PUBLIC, ctx.opResponse.visibility)
    }
}