package ru.otus.otuskotlin.mykotlin.common.models

data class MkplOp(
    var id: MkplOpId = MkplOpId.NONE,
    var orderNum: String = "",
    var title: String = "",
    var ownerId: MkplUserId = MkplUserId.NONE,
    var amount: MkplOpAmount = MkplOpAmount.NONE,
    var paymentId: MkplPaymentId = MkplPaymentId.NONE,
    var payment: MkplOpAmount = MkplOpAmount.NONE,
    var opType: MkplPaidType = MkplPaidType.NONE,
    var visibility: MkplVisibility = MkplVisibility.NONE,
    var lock: MkplOpLock = MkplOpLock.NONE,
    val permissionsClient: MutableSet<MkplOpPermissions> = mutableSetOf()
) {
    fun isEmpty() = this == NONE

    companion object {
        private val NONE = MkplOp()
    }

}
