package ru.otus.otuskotlin.mykotlin.repo.inmemory

import ru.otus.otuskotlin.mykotlin.common.models.*

data class OpEntity(
    val id: String? = null,
    val orderNum: String? = null,
    val title: String? = null,
    val ownerId: String? = null,
    val amount: Double? = null,
    val paymentId: String? = null,
    val payment: Double? = null,
    val opType: String? = null,
    val visibility: String? = null,
    val lock: String? = null,
) {
    constructor(model: MkpOp): this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        orderNum = model.orderNum.takeIf { it.isNotBlank() },
        title = model.title.takeIf { it.isNotBlank() },
        ownerId = model.ownerId.asString().takeIf { it.isNotBlank() },
        amount = model.amount.asDouble().takeIf { !it.isNaN() },
        paymentId = model.paymentId.asString().takeIf { it.isNotBlank() },
        payment = model.payment.asDouble().takeIf { !it.isNaN() },
        opType = model.opType.takeIf { it != MkpPaidType.NONE }?.name,
        visibility = model.visibility.takeIf { it != MkpVisibility.NONE }?.name,
        lock = model.lock.asString().takeIf { it.isNotBlank() }
        // Не нужно сохранять permissions, потому что он ВЫЧИСЛЯЕМЫЙ, а не хранимый
    )

    fun toInternal() = MkpOp(
        id = id?.let { MkpOpId(it) }?: MkpOpId.NONE,
        orderNum = orderNum?: "",
        title = title?: "",
        ownerId = ownerId?.let { MkpUserId(it) }?: MkpUserId.NONE,
        amount = amount?.let{ MkpOpAmount(it)} ?: MkpOpAmount.NONE,
        paymentId = paymentId?.let{ MkpPaymentId(it)} ?: MkpPaymentId.NONE,
        payment = payment?.let{ MkpOpAmount(it)} ?: MkpOpAmount.NONE,
        opType = opType?.let { MkpPaidType.valueOf(it) }?: MkpPaidType.NONE,
        visibility = visibility?.let { MkpVisibility.valueOf(it) }?: MkpVisibility.NONE,
        lock = lock?.let { MkpOpLock(it) } ?: MkpOpLock.NONE,
    )
}