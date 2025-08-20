package ru.otus.otuskotlin.mykotlin.common.models

data class MkplOpFilter(
    var searchString: String = "",
    var userId: MkplUserId = MkplUserId.NONE,
    var paidType: MkplPaidType = MkplPaidType.NONE,
)
