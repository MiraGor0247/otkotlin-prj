package ru.otus.otuskotlin.mykotlin.common.repo

import ru.otus.otuskotlin.mykotlin.common.models.MkpOp
import ru.otus.otuskotlin.mykotlin.common.models.MkpOpId
import ru.otus.otuskotlin.mykotlin.common.models.MkpOpLock

data class DbOpIdRequest(
    val id: MkpOpId,
    val lock: MkpOpLock = MkpOpLock.NONE,
) {
    constructor(ad: MkpOp): this(ad.id, ad.lock)
}
