package ru.otus.otuskotlin.mykotlin.common.models

data class MkpOpFilter(
    var searchString: String = "",
    var userId: MkpUserId = MkpUserId.NONE,
    var paidType: MkpPaidType = MkpPaidType.NONE,
)
