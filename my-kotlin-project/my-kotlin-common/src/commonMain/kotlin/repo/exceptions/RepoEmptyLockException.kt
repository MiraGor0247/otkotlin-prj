package ru.otus.otuskotlin.mykotlin.common.repo.exceptions

import ru.otus.otuskotlin.mykotlin.common.models.MkpOpId

class RepoEmptyLockException (id: MkpOpId): RepoOpException(
    id,
    "Lock is empty in DB"
)