package ru.otus.otuskotlin.mykotlin.repo.tests

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.mykotlin.common.models.MkpOp
import ru.otus.otuskotlin.mykotlin.common.repo.*
import ru.otus.otuskotlin.mykotlin.stubs.MkpOpStub

class OpRepositoryMockTest {
    private val repo = OpRepositoryMock(
        invokeCreateOp = { DbOpResponseOk(MkpOpStub.prepareResult { title = "create" }) },
        invokeReadOp = { DbOpResponseOk(MkpOpStub.prepareResult { title = "read" }) },
        invokeUpdateOp = { DbOpResponseOk(MkpOpStub.prepareResult { title = "update" }) },
        invokeDeleteOp = { DbOpResponseOk(MkpOpStub.prepareResult { title = "delete" }) },
        invokeSearchOp = { DbOpsResponseOk(listOf(MkpOpStub.prepareResult { title = "search" })) },
    )

    @Test
    fun mockCreate() = runTest {
        val result = repo.createOp(DbOpRequest(MkpOp()))
        assertIs<DbOpResponseOk>(result)
        assertEquals("create", result.data.title)
    }

    @Test
    fun mockRead() = runTest {
        val result = repo.readOp(DbOpIdRequest(MkpOp()))
        assertIs<DbOpResponseOk>(result)
        assertEquals("read", result.data.title)
    }

    @Test
    fun mockUpdate() = runTest {
        val result = repo.updateOP(DbOpRequest(MkpOp()))
        assertIs<DbOpResponseOk>(result)
        assertEquals("update", result.data.title)
    }

    @Test
    fun mockDelete() = runTest {
        val result = repo.deleteOp(DbOpIdRequest(MkpOp()))
        assertIs<DbOpResponseOk>(result)
        assertEquals("delete", result.data.title)
    }

    @Test
    fun mockSearch() = runTest {
        val result = repo.searchOp(DbOpFilterRequest())
        assertIs<DbOpsResponseOk>(result)
        assertEquals("search", result.data.first().title)
    }

}
