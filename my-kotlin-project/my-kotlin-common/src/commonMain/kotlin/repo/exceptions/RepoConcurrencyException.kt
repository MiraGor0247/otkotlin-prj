package ru.otus.otuskotlin.mykotlin.common.repo.exceptions

import ru.otus.otuskotlin.mykotlin.common.models.MkpOpId
import ru.otus.otuskotlin.mykotlin.common.models.MkpOpLock

class RepoConcurrencyException(id: MkpOpId, expectedLock: MkpOpLock, actualLock: MkpOpLock?): RepoOpException(
    id,
    "Expected lock is $expectedLock while actual lock in db is $actualLock"
)
