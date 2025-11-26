package ru.otus.otuskotlin.mykotlin.api.kmp


import ru.otus.otuskotlin.mykotlin.api.v2.models.OpCreateObject
import ru.otus.otuskotlin.mykotlin.api.v2.models.OpDeleteObject
import ru.otus.otuskotlin.mykotlin.api.v2.models.OpReadObject
import ru.otus.otuskotlin.mykotlin.api.v2.models.OpUpdateObject
import ru.otus.otuskotlin.mykotlin.common.models.*

fun MkpOp.toTransportCreateOp() = OpCreateObject(
    orderNum = orderNum,
    title = title,
    ownerId = ownerId.toTransportOp(),
    amount = amount.toTransportOp(),
    opType = opType.toTransportOp(),
    visibility = visibility.toTransportOp(),
    )

fun MkpOp.toTransportReadOp() = OpReadObject(
    id = id.toTransportOp()
)

fun MkpOp.toTransportUpdateOp() = OpUpdateObject(
    id = id.toTransportOp(),
    orderNum = orderNum,
    title = title,
    ownerId = ownerId.toTransportOp(),
    amount = amount.toTransportOp(),
    paymentId = paymentId.toTransportOp(),
    payment = payment.toTransportOp(),
    opType = opType.toTransportOp(),
    visibility = visibility.toTransportOp(),
    lock = lock.toTransportOp(),
)

internal fun MkpOpLock.toTransportOp() = takeIf { it != MkpOpLock.NONE }?.asString()

internal fun MkpUserId.toTransportOp() = takeIf { it != MkpUserId.NONE}?.asString()

internal fun MkpPaymentId.toTransportOp() = takeIf { it != MkpPaymentId.NONE}?.asString()

fun MkpOp.toTransportDeleteOp() = OpDeleteObject(
    id = id.toTransportOp(),
    lock = lock.toTransportOp(),
)