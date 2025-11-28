package ru.otus.otuskotlin.mykotlin.repo.tests

import ru.otus.otuskotlin.mykotlin.common.repo.*
import ru.otus.otuskotlin.mykotlin.common.models.*
import kotlin.test.*

abstract class RepoOpReadTest  {
    abstract val repo: IRepoOp
    protected open val readSucc = initObjects[0]

    @Test
    fun readSuccess() = runRepoTest {
        val result = repo.readOp(DbOpIdRequest(readSucc.id))

        assertIs<DbOpResponseOk>(result)
        assertEquals(readSucc, result.data)
    }

    @Test
    fun readNotFound() = runRepoTest {
        val result = repo.readOp(DbOpIdRequest(notFoundId))

        assertIs<DbOpResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertEquals("id", error?.field)
    }

    companion object : BaseInitOps("delete") {
        override val initObjects: List<MkpOp> = listOf(
            createInitTestModel("read")
        )

        val notFoundId = MkpOpId("op-repo-read-notFound")

    }
}
