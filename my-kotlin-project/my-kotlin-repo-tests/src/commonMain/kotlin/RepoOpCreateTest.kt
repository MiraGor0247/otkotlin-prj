package ru.otus.otuskotlin.mykotlin.repo.tests

import ru.otus.otuskotlin.mykotlin.common.models.*
import ru.otus.otuskotlin.mykotlin.repo.common.IRepoOpInitializable
import kotlin.test.*
import ru.otus.otuskotlin.mykotlin.common.repo.*

abstract class RepoOpCreateTest {
    abstract val repo: IRepoOpInitializable
    protected open val uuidNew = MkpOpId("10000000-0000-0000-0000-000000000001")

    private val createObj = MkpOp(
        orderNum = "001",
        title = "create object",
        ownerId = MkpUserId("owner-123"),
        amount = MkpOpAmount(1200.0),
        visibility = MkpVisibility.VISIBLE_TO_REGISTERED,
        opType = MkpPaidType.UNPAID,
    )

    @Test
    fun createSuccess() = runRepoTest {
        val result = repo.createOp(DbOpRequest(createObj))
        val expected = createObj
        assertIs<DbOpResponseOk>(result)
        assertEquals(uuidNew, result.data.id)
        assertEquals(expected.orderNum, result.data.orderNum)
        assertEquals(expected.title, result.data.title)
        assertEquals(expected.amount, result.data.amount)
        assertEquals(expected.opType, result.data.opType)
        assertNotEquals(MkpOpId.NONE, result.data.id)
    }

    companion object : BaseInitOps("create") {
        override val initObjects: List<MkpOp> = emptyList()
    }
}
