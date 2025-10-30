package ru.otus.otuskotlin.mykotlin.repo.tests

import ru.otus.otuskotlin.mykotlin.common.models.MkpOp
import ru.otus.otuskotlin.mykotlin.common.models.MkpOpId
import ru.otus.otuskotlin.mykotlin.common.repo.*
import ru.otus.otuskotlin.mykotlin.repo.tests.RepoOpCreateTest.Companion.lockOld
import kotlin.test.*

abstract class RepoOpDeleteTest  {
    abstract val repo: IRepoOp
    protected open val deleteSucc = initObjects[0]
    protected open val deleteConc = initObjects[1]
    protected open val notFoundId = MkpOpId("ad-repo-delete-notFound")

    @Test
    fun deleteSuccess() = runRepoTest {
        val lockOld = deleteSucc.lock
        val result = repo.deleteOp(DbOpIdRequest(deleteSucc.id, lock = lockOld))
        assertIs<DbOpResponseOk>(result)
        assertEquals(deleteSucc.title, result.data.title)
        assertEquals(deleteSucc.orderNum, result.data.orderNum)
    }

    @Test
    fun deleteNotFound() = runRepoTest {
        val result = repo.readOp(DbOpIdRequest(notFoundId, lock = lockOld))

        assertIs<DbOpResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertNotNull(error)
    }

    @Test
    fun deleteConcurrency() = runRepoTest {
        val result = repo.deleteOp(DbOpIdRequest(deleteConc.id, lock = lockBad))

        assertIs<DbOpResponseErrWithData>(result)
        val error = result.errors.find { it.code == "repo-concurrency" }
        assertNotNull(error)
    }

    companion object : BaseInitOps("delete") {
        override val initObjects: List<MkpOp> = listOf(
            createInitTestModel("delete"),
            createInitTestModel("deleteLock"),
        )
    }
}
