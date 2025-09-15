package ru.otus.otuskotlin.mykotlin.api.log

import kotlinx.datetime.Clock
import ru.otus.otuskotlin.mykotlin.api.log.models.*
import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.models.*


fun MkpContext.toLog(logId: String) = CommonLogModel(
    messageTime = Clock.System.now().toString(),
    logId = logId,
    source = "my-kotlin",
    op = toMkpLog(),
    errors = errors.map { it.toLog() },
)

private fun MkpContext.toMkpLog(): MkpLogModel? {
    val opNone = MkpOp()
    return MkpLogModel(
        requestId = requestId.takeIf { it != MkpRequestId.NONE }?.asString(),
        requestOp = opRequest.takeIf { it != opNone }?.toLog(),
        responseOp= opResponse.takeIf { it != opNone }?.toLog(),
        responseOps = opsResponse.takeIf { it.isNotEmpty() }?.filter { it != opNone }?.map { it.toLog() },
        requestFilter = opFilterRequest.takeIf { it != MkpOpFilter() }?.toLog(),
    ).takeIf { it != MkpLogModel() }
}

private fun MkpOpFilter.toLog() = OpFilterLog(
    searchString = searchString.takeIf { it.isNotBlank() },
    ownerId = userId.takeIf { it != MkpUserId.NONE }?.asString(),
    opType = paidType.takeIf { it != MkpPaidType.NONE }?.name,
)

private fun MkpError.toLog() = ErrorLogModel(
    message = message.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    code = code.takeIf { it.isNotBlank() },
)

private fun MkpOp.toLog() = OpLog(
    id = id.takeIf { it != MkpOpId.NONE }?.asString(),
    orderNum = orderNum.takeIf { it.isNotBlank() },
    title = title.takeIf { it.isNotBlank() },
    ownerId = ownerId.takeIf { it != MkpUserId.NONE }?.asString(),
    amount = amount.takeIf { it != MkpOpAmount.NONE }?.asDouble(),
    paymentId = paymentId.takeIf { it != MkpPaymentId.NONE }?.asString(),
    opType = opType.takeIf { it != MkpPaidType.NONE }?.name,
)
