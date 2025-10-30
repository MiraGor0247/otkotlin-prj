package ru.otus.otuskotlin.mykotlin.common.repo.exceptions

import ru.otus.otuskotlin.mykotlin.common.models.MkpOpId

open class RepoOpException(
    @Suppress("unused")
    val adId: MkpOpId,
    msg: String,
): RepoException(msg)