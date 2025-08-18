package ru.otus.otuskotlin.mykotlin.mappers.v1

import ru.otus.otuskotlin.mykotlin.api.v1.models.OpCreateObject
import ru.otus.otuskotlin.mykotlin.api.v1.models.OpDeleteObject
import ru.otus.otuskotlin.mykotlin.api.v1.models.OpReadObject
import ru.otus.otuskotlin.mykotlin.api.v1.models.OpUpdateObject
import ru.otus.otuskotlin.mykotlin.common.models.*

fun MkplOp.toTransportCreateOp() = OpCreateObject(
    orderNum = orderNum,
    title = title,
    ownerId = ownerId.toTransportOp(),
    amount = amount.toTransportOp(),
    opType = opType.toTransportOp(),
    visibility = visibility.toTransportOp(),
    )

fun MkplOp.toTransportReadOp() = OpReadObject(
    id = id.toTransportOp()
)

fun MkplOp.toTransportUpdateOp() = OpUpdateObject(
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

internal fun MkplOpLock.toTransportOp() = takeIf { it != MkplOpLock.NONE }?.asString()

internal fun MkplUserId.toTransportOp() = takeIf { it != MkplUserId.NONE}?.asString()

internal fun MkplPaymentId.toTransportOp() = takeIf { it != MkplPaymentId.NONE}?.asString()

fun MkplOp.toTransportDeleteOp() = OpDeleteObject(
    id = id.toTransportOp(),
    lock = lock.toTransportOp(),
)