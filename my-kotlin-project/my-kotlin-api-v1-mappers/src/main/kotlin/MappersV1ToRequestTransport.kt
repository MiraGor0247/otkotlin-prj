package ru.otus.otuskotlin.mykotlin.api.v1.mappers

import ru.otus.otuskotlin.mykotlin.api.v1.models.OpCreateObject
import ru.otus.otuskotlin.mykotlin.api.v1.models.OpDeleteObject
import ru.otus.otuskotlin.mykotlin.api.v1.models.OpReadObject
import ru.otus.otuskotlin.mykotlin.api.v1.models.OpUpdateObject
import ru.otus.otuskotlin.mykotlin.common.models.MkpOp
import ru.otus.otuskotlin.mykotlin.common.models.MkpOpId
import ru.otus.otuskotlin.mykotlin.common.models.MkpOpLock

fun MkpOp.toTransportCreate() = OpCreateObject(
    orderNum = orderNum.takeIf {  it.isNotBlank() },
    title = title.takeIf { it.isNotBlank() },
    ownerId = ownerId.toTransportOp(),
    amount = amount.toTransportOp(),
    opType = opType.toTransportOp(),
    visibility = visibility.toTransportOp(),
)

fun MkpOp.toTransportRead() = OpReadObject(
    id = id.takeIf { it != MkpOpId.NONE }?.asString(),
)

fun MkpOp.toTransportUpdate() = OpUpdateObject(
    id = id.takeIf { it != MkpOpId.NONE }?.asString(),
    orderNum = orderNum.takeIf {  it.isNotBlank() },
    title = title.takeIf { it.isNotBlank() },
    ownerId = ownerId.toTransportOp(),
    amount = amount.toTransportOp(),
    paymentId = paymentId.toTransportOp(),
    payment = payment.toTransportOp(),
    opType = opType.toTransportOp(),
    visibility = visibility.toTransportOp(),
    lock = lock.takeIf { it != MkpOpLock.NONE }?.asString(),
)

fun MkpOp.toTransportDelete() = OpDeleteObject(
    id = id.takeIf { it != MkpOpId.NONE }?.asString(),
    lock = lock.takeIf { it != MkpOpLock.NONE }?.asString(),
)