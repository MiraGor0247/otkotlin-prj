package ru.otus.otuskotlin.mykotlin.business.repo

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import ru.otus.otuskotlin.mykotlin.repo.tests.OpRepositoryMock
import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.MkpCorSettings
import ru.otus.otuskotlin.mykotlin.common.models.*
import ru.otus.otuskotlin.mykotlin.common.repo.DbOpResponseOk
import ru.otus.otuskotlin.mykotlin.kotlin.MkpOpProcessor

class RepoUpdateBusinessTests {
    private val userId = MkpUserId("321")
    private val command = MkpCommand.UPDATE

    private val initOp = MkpOp(
        id = MkpOpId("123"),
        orderNum = "101",
        title = "abc",
        amount = MkpOpAmount(12000.0),
        ownerId = userId,
        opType = MkpPaidType.PAID,
        visibility = MkpVisibility.VISIBLE_PUBLIC,
        lock = MkpOpLock("123-234-abc-ABC"),
    )
    private val repo = OpRepositoryMock(
        invokeReadOp = {
            DbOpResponseOk(
                data = initOp,
            )
        },
        invokeUpdateOp = {
            DbOpResponseOk(
                data = MkpOp(
                    id = MkpOpId("123"),
                    orderNum = "101",
                    title = "abc",
                    amount = MkpOpAmount(12999.0),
                    opType = MkpPaidType.PAID,
                    visibility = MkpVisibility.VISIBLE_TO_REGISTERED,
                    lock = MkpOpLock("123-234-abc-ABC"),
                )
            )
        }
    )
    private val settings = MkpCorSettings(repoTest = repo)
    private val processor = MkpOpProcessor(settings)

    @Test
    fun repoUpdateSuccessTest() = runTest {
        val opToUpdate = MkpOp(
            id = MkpOpId("123"),
            orderNum = "101",
            title = "abc",
            amount = MkpOpAmount(12999.0),
            opType = MkpPaidType.PAID,
            visibility = MkpVisibility.VISIBLE_TO_REGISTERED,
            lock = MkpOpLock("123-234-abc-ABC")
        )
        val ctx = MkpContext(
            command = command,
            state = MkpState.NONE,
            workMode = MkpWorkMode.TEST,
            opRequest = opToUpdate,
        )
        processor.exec(ctx)
        assertEquals(MkpState.FINISHING, ctx.state)
        assertEquals(opToUpdate.id, ctx.opResponse.id)
        assertEquals(opToUpdate.title, ctx.opResponse.title)
        assertEquals(opToUpdate.orderNum, ctx.opResponse.orderNum)
        assertEquals(opToUpdate.amount, ctx.opResponse.amount)
        assertEquals(opToUpdate.opType, ctx.opResponse.opType)
        assertEquals(opToUpdate.visibility, ctx.opResponse.visibility)
    }
}