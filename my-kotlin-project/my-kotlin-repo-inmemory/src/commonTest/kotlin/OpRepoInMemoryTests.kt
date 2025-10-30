package ru.otus.otuskotlin.mykotlin.repo.inmemory

import ru.otus.otuskotlin.mykotlin.repo.common.OpRepoInitialized
import ru.otus.otuskotlin.mykotlin.repo.tests.*

class OpRepoInMemoryCreateTest : RepoOpCreateTest() {
    override val repo = OpRepoInitialized(
        OpRepoInMemory(randomUuid = { uuidNew.asString() }),
        initObjects = initObjects,
    )
}

class OpRepoInMemoryDeleteTest : RepoOpDeleteTest() {
    override val repo = OpRepoInitialized(
        OpRepoInMemory(),
        initObjects = initObjects,
    )
}

class OpRepoInMemoryReadTest : RepoOpReadTest() {
    override val repo = OpRepoInitialized(
        OpRepoInMemory(),
        initObjects = initObjects,
    )
}

class OpRepoInMemorySearchTest : RepoOpSearchTest() {
    override val repo = OpRepoInitialized(
        OpRepoInMemory(),
        initObjects = initObjects,
    )
}

class OpRepoInMemoryUpdateTest : RepoOpUpdateTest() {
    override val repo = OpRepoInitialized(
        OpRepoInMemory(randomUuid = { lockNew.asString() }),
        initObjects = initObjects,
    )
}