package ru.otus.otuskotlin.mykotlin.repo.tests

import ru.otus.otuskotlin.mykotlin.common.models.*
import ru.otus.otuskotlin.mykotlin.repo.common.IRepoOpInitializable
import kotlin.test.*
import ru.otus.otuskotlin.mykotlin.common.repo.*

abstract class RepoOpUpdateTest {
    abstract val repo: IRepoOp
    protected open val updateSucc = initObjects[0]
    protected open val updateConc = initObjects[1]
    protected val updateIdNotFound = MkpOpId("op-repo-update-not-found")
    protected val lockBad = MkpOpLock("20000000-0000-0000-0000-000000000009")
    protected val lockNew = MkpOpLock("20000000-0000-0000-0000-000000000002")

    private val reqUpdateSucc by lazy {
        MkpOp(
            id = updateSucc.id,
            orderNum = "001",
            title = "create object",
            ownerId = MkpUserId("owner-123"),
            amount = MkpOpAmount(1200.0),
            visibility = MkpVisibility.VISIBLE_TO_REGISTERED,
            opType = MkpPaidType.UNPAID,
            lock = initObjects.first().lock,
        )
    }
    private val reqUpdateNotFound = MkpOp(
        id = updateIdNotFound,
        orderNum = "update object not found number",
        title = "update object not found",
        ownerId = MkpUserId("owner-123"),
        visibility = MkpVisibility.VISIBLE_TO_REGISTERED,
        opType = MkpPaidType.UNPAID,
        lock = initObjects.first().lock,
    )
    private val reqUpdateConc by lazy {
        MkpOp(
            id = updateConc.id,
            orderNum = "update object not found number",
            title = "update object not found",
            ownerId = MkpUserId("owner-123"),
            visibility = MkpVisibility.VISIBLE_TO_REGISTERED,
            opType = MkpPaidType.UNPAID,
            lock = lockBad,
        )
    }

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateOP(DbOpRequest(reqUpdateSucc))
        assertIs<DbOpResponseOk>(result)
        assertEquals(reqUpdateSucc.id, result.data.id)
        assertEquals(reqUpdateSucc.orderNum, result.data.orderNum)
        assertEquals(reqUpdateSucc.title, result.data.title)
        assertEquals(reqUpdateSucc.amount, result.data.amount)
        assertEquals(reqUpdateSucc.opType, result.data.opType)
        assertEquals(lockNew, result.data.lock)
    }

    @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateOP(DbOpRequest(reqUpdateNotFound))
        assertIs<DbOpResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertEquals("id", error?.field)
    }

    @Test
    fun updateConcurrencyError() = runRepoTest {
        val result = repo.updateOP(DbOpRequest(reqUpdateConc))
        assertIs<DbOpResponseErrWithData>(result)
        val error = result.errors.find { it.code == "repo-concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(updateConc, result.data)
    }

    companion object : BaseInitOps("update") {
        override val initObjects: List<MkpOp> = listOf(
            createInitTestModel("update"),
            createInitTestModel("updateConc"),
        )
    }
}
