package ru.otus.otuskotlin.mykotlin.mappers.v1

import ru.otus.otuskotlin.mykotlin.api.v1.models.*
import ru.otus.otuskotlin.mykotlin.common.MkplContext
import ru.otus.otuskotlin.mykotlin.common.exceptions.UnknownMkplCommand
import ru.otus.otuskotlin.mykotlin.common.models.*

fun MkplContext.toTransportOp(): IResponse = when (val cmd = command) {
    MkplCommand.CREATE -> toTransportCreate()
    MkplCommand.READ -> toTransportRead()
    MkplCommand.UPDATE -> toTransportUpdate()
    MkplCommand.DELETE -> toTransportDelete()
    MkplCommand.SEARCH -> toTransportSearch()
    MkplCommand.NONE -> throw UnknownMkplCommand(cmd)
}

fun MkplContext.toTransportCreate() = OpCreateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    op = opResponse.toTransportOp()
)

fun MkplContext.toTransportRead() = OpReadResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    op = opResponse.toTransportOp()
)

fun MkplContext.toTransportUpdate() = OpUpdateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    op = opResponse.toTransportOp()
)

fun MkplContext.toTransportDelete() = OpDeleteResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    op = opResponse.toTransportOp()
)

fun MkplContext.toTransportSearch() = OpSearchResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    ops = opsResponse.toTransportOp()
)

fun List<MkplOp>.toTransportOp(): List<OpResponseObject>? = this
    .map { it.toTransportOp() }
    .toList()
    .takeIf { it.isNotEmpty() }

fun MkplOp.toTransportOp(): OpResponseObject = OpResponseObject(
    id = id.toTransportOp(),
    orderNum = orderNum.takeIf { it.isNotBlank() },
    title = title.takeIf { it.isNotBlank() },
    ownerId = ownerId.takeIf { it != MkplUserId.NONE }?.asString(),
    amount = amount.toTransportOp(),
    paymentId = paymentId.takeIf { it != MkplPaymentId.NONE }?.asString(),
    payment = payment.toTransportOp(),
    opType = opType.toTransportOp(),
    visibility = visibility.toTransportOp(),
    permissions = permissionsClient.toTransportOp(),
)

internal fun MkplOpId.toTransportOp() = takeIf { it != MkplOpId.NONE }?.asString()

internal fun MkplOpAmount.toTransportOp() = takeIf { it != MkplOpAmount.NONE }?.asDouble()

private fun Set<MkplOpPermissions>.toTransportOp(): Set<OpPermissions>? = this
    .map { it.toTransportOp() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun MkplOpPermissions.toTransportOp() = when (this) {
    MkplOpPermissions.READ -> OpPermissions.READ
    MkplOpPermissions.UPDATE -> OpPermissions.UPDATE
    MkplOpPermissions.MAKE_VISIBLE_ADMIN -> OpPermissions.MAKE_VISIBLE_ADMIN
    MkplOpPermissions.MAKE_VISIBLE_REGISTERED -> OpPermissions.MAKE_VISIBLE_REGISTERED
    MkplOpPermissions.MAKE_VISIBLE_PUBLIC -> OpPermissions.MAKE_VISIBLE_PUBLIC
    MkplOpPermissions.DELETE -> OpPermissions.DELETE
}

internal fun MkplVisibility.toTransportOp(): OpVisibility? = when (this) {
    MkplVisibility.VISIBLE_PUBLIC -> OpVisibility.PUBLIC
    MkplVisibility.VISIBLE_TO_REGISTERED -> OpVisibility.REGISTERED_ONLY
    MkplVisibility.VISIBLE_TO_ADMIN -> OpVisibility.ADMIN_ONLY
    MkplVisibility.NONE -> null
}

internal fun MkplPaidType.toTransportOp(): PaidType? = when (this) {
    MkplPaidType.PAID -> PaidType.PAID
    MkplPaidType.UNPAID -> PaidType.UNPAID
    MkplPaidType.NONE -> null
}

private fun List<MkplError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportOp() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun MkplError.toTransportOp() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)

private fun MkplState.toResult(): ResponseResult? = when (this) {
    MkplState.RUNNING -> ResponseResult.SUCCESS
    MkplState.FAILING -> ResponseResult.ERROR
    MkplState.FINISHING -> ResponseResult.SUCCESS
    MkplState.NONE -> null
}
