package ru.otus.otuskotlin.mykotlin.business.repo

import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.MkpCorSettings
import ru.otus.otuskotlin.mykotlin.common.models.*
import ru.otus.otuskotlin.mykotlin.common.repo.DbOpResponseOk
import ru.otus.otuskotlin.mykotlin.common.repo.errorNotFound
import ru.otus.otuskotlin.mykotlin.kotlin.MkpOpProcessor
import ru.otus.otuskotlin.mykotlin.repo.tests.OpRepositoryMock
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

private val initOp = MkpOp(
    id = MkpOpId("123"),
    orderNum = "orderNum 101",
    title = "abc",
    amount = MkpOpAmount(12000.0),
    opType = MkpPaidType.PAID,
    visibility = MkpVisibility.VISIBLE_PUBLIC,
)
private val repo = OpRepositoryMock(
    invokeReadOp = {
        if (it.id == initOp.id) {
            DbOpResponseOk (
                data = initOp,
            )
        } else errorNotFound(it.id)
    }
)
private val settings = MkpCorSettings(repoTest = repo)
private val processor = MkpOpProcessor(settings)

fun repoNotFoundTest(command: MkpCommand) = runTest {
    val ctx = MkpContext(
        command = command,
        state = MkpState.NONE,
        workMode = MkpWorkMode.TEST,
        opRequest = MkpOp(
            id = MkpOpId("12345"),
            orderNum = "number 1",
            title = "zzz",
            amount = MkpOpAmount(1000.0),
            opType = MkpPaidType.PAID,
            visibility = MkpVisibility.VISIBLE_TO_REGISTERED,
            lock = MkpOpLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(MkpState.FAILING, ctx.state)
    assertEquals(MkpOp(), ctx.opResponse)
    assertEquals(1, ctx.errors.size)
    assertNotNull(ctx.errors.find { it.code == "repo-not-found" }, "Errors must contain not-found")
}