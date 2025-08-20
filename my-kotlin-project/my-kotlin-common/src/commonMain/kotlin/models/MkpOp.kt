package ru.otus.otuskotlin.mykotlin.common.models

data class MkpOp(
    var id: MkpOpId = MkpOpId.NONE,
    var orderNum: String = "",
    var title: String = "",
    var ownerId: MkpUserId = MkpUserId.NONE,
    var amount: MkpOpAmount = MkpOpAmount.NONE,
    var paymentId: MkpPaymentId = MkpPaymentId.NONE,
    var payment: MkpOpAmount = MkpOpAmount.NONE,
    var opType: MkpPaidType = MkpPaidType.NONE,
    var visibility: MkpVisibility = MkpVisibility.NONE,
    var lock: MkpOpLock = MkpOpLock.NONE,
    val permissionsClient: MutableSet<MkpOpPermissions> = mutableSetOf()
) {
    fun isEmpty() = this == NONE

    companion object {
        private val NONE = MkpOp()
    }

}
