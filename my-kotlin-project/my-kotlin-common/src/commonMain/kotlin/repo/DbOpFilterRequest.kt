package ru.otus.otuskotlin.mykotlin.common.repo

import ru.otus.otuskotlin.mykotlin.common.models.MkpPaidType
import ru.otus.otuskotlin.mykotlin.common.models.MkpUserId

data class DbOpFilterRequest (
    val titleFilter: String = "",
    val ownerId: MkpUserId = MkpUserId.NONE,
    val opType: MkpPaidType = MkpPaidType.NONE,
)
