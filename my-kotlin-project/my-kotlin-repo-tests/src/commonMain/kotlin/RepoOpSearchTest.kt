package ru.otus.otuskotlin.mykotlin.repo.tests

import ru.otus.otuskotlin.mykotlin.common.models.*
import ru.otus.otuskotlin.mykotlin.repo.common.IRepoOpInitializable
import kotlin.test.*
import ru.otus.otuskotlin.mykotlin.common.repo.*

abstract class RepoOpSearchTest  {
    abstract val repo: IRepoOp

    protected open val initializedObjects: List<MkpOp> = initObjects

    @Test
    fun searchOwner() = runRepoTest {
        val result = repo.searchOp(DbOpFilterRequest(ownerId = searchOwnerId))
        assertIs<DbOpsResponseOk>(result)
        val expected = listOf(initializedObjects[1], initializedObjects[3]).sortedBy { it.id.asString() }
        assertEquals(expected, result.data.sortedBy { it.id.asString() })
    }

    @Test
    fun searchPaidType() = runRepoTest {
        val result = repo.searchOp(DbOpFilterRequest(opType = MkpPaidType.PAID))
        assertIs<DbOpsResponseOk>(result)
        val expected = listOf(initializedObjects[2], initializedObjects[4]).sortedBy { it.id.asString() }
        assertEquals(expected, result.data.sortedBy { it.id.asString()})
    }


    companion object: BaseInitOps("search") {

        val searchOwnerId = MkpUserId("owner-124")
        override val initObjects: List<MkpOp> = listOf(
            createInitTestModel("op1"),
            createInitTestModel("op2", ownerId = searchOwnerId),
            createInitTestModel("op3", opType = MkpPaidType.PAID),
            createInitTestModel("op4", ownerId = searchOwnerId),
            createInitTestModel("op5", opType = MkpPaidType.PAID),
        )
    }
}
