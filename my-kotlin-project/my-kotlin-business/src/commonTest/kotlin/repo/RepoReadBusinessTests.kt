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

class RepoReadBusinessTests {
    private val userId = MkpUserId("321")
    private val command = MkpCommand.READ

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
        invokeReadOp = {
            DbOpResponseOk(
                data = initOp,
            )
        }
    )
    private val settings = MkpCorSettings(repoTest = repo)
    private val processor = MkpOpProcessor(settings)

    @Test
    fun repoReadSuccessTest() = runTest {
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
        assertEquals(initOp.id, ctx.opResponse.id)
        assertEquals(initOp.title, ctx.opResponse.title)
        assertEquals(initOp.orderNum, ctx.opResponse.orderNum)
        assertEquals(initOp.opType, ctx.opResponse.opType)
        assertEquals(initOp.visibility, ctx.opResponse.visibility)
    }
    fun repoReadNotFoundTest() = repoNotFoundTest(command)
}