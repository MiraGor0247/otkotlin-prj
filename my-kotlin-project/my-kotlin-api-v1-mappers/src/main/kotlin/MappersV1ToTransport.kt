package ru.otus.otuskotlin.mykotlin.mappers.v1

import ru.otus.otuskotlin.mykotlin.api.v1.models.*
import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.exceptions.UnknownMkpCommand
import ru.otus.otuskotlin.mykotlin.common.models.*

fun MkpContext.toTransportOp(): IResponse = when (val cmd = command) {
    MkpCommand.CREATE -> toTransportCreate()
    MkpCommand.READ -> toTransportRead()
    MkpCommand.UPDATE -> toTransportUpdate()
    MkpCommand.DELETE -> toTransportDelete()
    MkpCommand.SEARCH -> toTransportSearch()
    MkpCommand.NONE -> throw UnknownMkpCommand(cmd)
}

fun MkpContext.toTransportCreate() = OpCreateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    op = opResponse.toTransportOp()
)

fun MkpContext.toTransportRead() = OpReadResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    op = opResponse.toTransportOp()
)

fun MkpContext.toTransportUpdate() = OpUpdateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    op = opResponse.toTransportOp()
)

fun MkpContext.toTransportDelete() = OpDeleteResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    op = opResponse.toTransportOp()
)

fun MkpContext.toTransportSearch() = OpSearchResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    ops = opsResponse.toTransportOp()
)

fun List<MkpOp>.toTransportOp(): List<OpResponseObject>? = this
    .map { it.toTransportOp() }
    .toList()
    .takeIf { it.isNotEmpty() }

fun MkpOp.toTransportOp(): OpResponseObject = OpResponseObject(
    id = id.toTransportOp(),
    orderNum = orderNum.takeIf { it.isNotBlank() },
    title = title.takeIf { it.isNotBlank() },
    ownerId = ownerId.takeIf { it != MkpUserId.NONE }?.asString(),
    amount = amount.toTransportOp(),
    paymentId = paymentId.takeIf { it != MkpPaymentId.NONE }?.asString(),
    payment = payment.toTransportOp(),
    opType = opType.toTransportOp(),
    visibility = visibility.toTransportOp(),
    permissions = permissionsClient.toTransportOp(),
)

internal fun MkpOpId.toTransportOp() = takeIf { it != MkpOpId.NONE }?.asString()

internal fun MkpOpAmount.toTransportOp() = takeIf { it != MkpOpAmount.NONE }?.asDouble()

private fun Set<MkpOpPermissions>.toTransportOp(): Set<OpPermissions>? = this
    .map { it.toTransportOp() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun MkpOpPermissions.toTransportOp() = when (this) {
    MkpOpPermissions.READ -> OpPermissions.READ
    MkpOpPermissions.UPDATE -> OpPermissions.UPDATE
    MkpOpPermissions.MAKE_VISIBLE_ADMIN -> OpPermissions.MAKE_VISIBLE_ADMIN
    MkpOpPermissions.MAKE_VISIBLE_REGISTERED -> OpPermissions.MAKE_VISIBLE_REGISTERED
    MkpOpPermissions.MAKE_VISIBLE_PUBLIC -> OpPermissions.MAKE_VISIBLE_PUBLIC
    MkpOpPermissions.DELETE -> OpPermissions.DELETE
}

internal fun MkpVisibility.toTransportOp(): OpVisibility? = when (this) {
    MkpVisibility.VISIBLE_PUBLIC -> OpVisibility.PUBLIC
    MkpVisibility.VISIBLE_TO_REGISTERED -> OpVisibility.REGISTERED_ONLY
    MkpVisibility.VISIBLE_TO_ADMIN -> OpVisibility.ADMIN_ONLY
    MkpVisibility.NONE -> null
}

internal fun MkpPaidType.toTransportOp(): PaidType? = when (this) {
    MkpPaidType.PAID -> PaidType.PAID
    MkpPaidType.UNPAID -> PaidType.UNPAID
    MkpPaidType.NONE -> null
}

private fun List<MkpError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportOp() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun MkpError.toTransportOp() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)

private fun MkpState.toResult(): ResponseResult? = when (this) {
    MkpState.RUNNING -> ResponseResult.SUCCESS
    MkpState.FAILING -> ResponseResult.ERROR
    MkpState.FINISHING -> ResponseResult.SUCCESS
    MkpState.NONE -> null
}
