package ru.otus.otuskotlin.mykotlin.common.repo

import ru.otus.otuskotlin.mykotlin.common.helpers.errorSystem
import ru.otus.otuskotlin.mykotlin.common.models.MkpError
import ru.otus.otuskotlin.mykotlin.common.models.MkpOp
import ru.otus.otuskotlin.mykotlin.common.models.MkpOpId
import ru.otus.otuskotlin.mykotlin.common.models.MkpOpLock
import ru.otus.otuskotlin.mykotlin.common.repo.exceptions.RepoConcurrencyException
import ru.otus.otuskotlin.mykotlin.common.repo.exceptions.RepoException

const val ERROR_GROUP_REPO = "repo"

fun errorNotFound(id: MkpOpId) = DbOpResponseErr(
    MkpError(
        code = "$ERROR_GROUP_REPO-not-found",
        group = ERROR_GROUP_REPO,
        field = "id",
        message = "Object with ID: ${id.asString()} is not Found",
    )
)

val errorEmptyId = DbOpResponseErr(
    MkpError(
        code = "$ERROR_GROUP_REPO-empty-id",
        group = ERROR_GROUP_REPO,
        field = "id",
        message = "Id must not be null or blank"
    )
)

fun errorRepoConcurrency(
    oldAd: MkpOp,
    expectedLock: MkpOpLock,
    exception: Exception = RepoConcurrencyException(
        id = oldAd.id,
        expectedLock = expectedLock,
        actualLock = oldAd.lock,
    ),
) = DbOpResponseErrWithData(
    ad = oldAd,
    err = MkpError(
        code = "$ERROR_GROUP_REPO-concurrency",
        group = ERROR_GROUP_REPO,
        field = "lock",
        message = "The object with ID ${oldAd.id.asString()} has been changed concurrently by another user or process",
        exception = exception,
    )
)

fun errorEmptyLock(id: MkpOpId) = DbOpResponseErr(
    MkpError(
        code = "$ERROR_GROUP_REPO-lock-empty",
        group = ERROR_GROUP_REPO,
        field = "lock",
        message = "Lock for Ad ${id.asString()} is empty that is not admitted"
    )
)

fun errorDb(e: RepoException) = DbOpResponseErr(
    errorSystem(
        violationCode = "dbLockEmpty",
        e = e
    )
)