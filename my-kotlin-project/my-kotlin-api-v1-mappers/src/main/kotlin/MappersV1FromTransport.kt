package ru.otus.otuskotlin.mykotlin.mappers.v1

import ru.otus.otuskotlin.mykotlin.api.v1.models.*
import ru.otus.otuskotlin.mykotlin.common.MkplContext
import ru.otus.otuskotlin.mykotlin.common.models.*
import ru.otus.otuskotlin.mykotlin.common.models.MkplWorkMode
import ru.otus.otuskotlin.mykotlin.common.stubs.MkplStubs
import ru.otus.otuskotlin.mykotlin.mappers.v1.exceptions.UnknownRequestClass

fun MkplContext.fromTransport(request: IRequest) = when (request) {
    is OpCreateRequest -> fromTransport(request)
    is OpReadRequest -> fromTransport(request)
    is OpUpdateRequest -> fromTransport(request)
    is OpDeleteRequest -> fromTransport(request)
    is OpSearchRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

private fun String?.toOpId() = this?.let { MkplOpId(it) } ?: MkplOpId.NONE
private fun String?.toOpWithId() = MkplOp(id = this.toOpId())
private fun String?.toOpLock() = this?.let { MkplOpLock(it) } ?: MkplOpLock.NONE
private fun String?.toUserId() = this?.let { MkplUserId(it) } ?: MkplUserId.NONE
private fun Double?.toAmount() = this?.let { MkplOpAmount(it) } ?: MkplOpAmount.NONE
private fun String?.toPaymentId() = this?.let { MkplPaymentId(it) } ?: MkplPaymentId.NONE

private fun OpDebug?.transportToWorkMode(): MkplWorkMode = when (this?.mode) {
    OpRequestDebugMode.PROD -> MkplWorkMode.PROD
    OpRequestDebugMode.TEST -> MkplWorkMode.TEST
    OpRequestDebugMode.STUB -> MkplWorkMode.STUB
    null -> MkplWorkMode.PROD
}

private fun OpDebug?.transportToStubCase(): MkplStubs = when (this?.stub) {
    OpRequestDebugStubs.SUCCESS -> MkplStubs.SUCCESS
    OpRequestDebugStubs.NOT_FOUND -> MkplStubs.NOT_FOUND
    OpRequestDebugStubs.BAD_ID -> MkplStubs.BAD_ID
    OpRequestDebugStubs.BAD_TITLE -> MkplStubs.BAD_TITLE
    OpRequestDebugStubs.BAD_NUMBER -> MkplStubs.BAD_NUMBER
    OpRequestDebugStubs.BAD_VISIBILITY -> MkplStubs.BAD_VISIBILITY
    OpRequestDebugStubs.CANNOT_DELETE -> MkplStubs.CANNOT_DELETE
    OpRequestDebugStubs.BAD_SEARCH_STRING -> MkplStubs.BAD_SEARCH_STRING
    null -> MkplStubs.NONE
}

fun MkplContext.fromTransport(request: OpCreateRequest) {
    command = MkplCommand.CREATE
    opRequest = request.op?.toInternal() ?: MkplOp()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun MkplContext.fromTransport(request: OpReadRequest) {
    command = MkplCommand.READ
    opRequest = request.op.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun OpReadObject?.toInternal(): MkplOp = if (this != null) {
    MkplOp(id = id.toOpId())
} else {
    MkplOp()
}


fun MkplContext.fromTransport(request: OpUpdateRequest) {
    command = MkplCommand.UPDATE
    opRequest = request.op?.toInternal() ?: MkplOp()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun MkplContext.fromTransport(request: OpDeleteRequest) {
    command = MkplCommand.DELETE
    opRequest = request.op.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun OpDeleteObject?.toInternal(): MkplOp = if (this != null) {
    MkplOp(
        id = id.toOpId(),
        lock = lock.toOpLock(),
    )
} else {
    MkplOp()
}

fun MkplContext.fromTransport(request: OpSearchRequest) {
    command = MkplCommand.SEARCH
    opFilterRequest = request.opFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}


private fun OpSearchFilter?.toInternal(): MkplOpFilter = MkplOpFilter(
    searchString = this?.searchString ?: ""
)

private fun OpCreateObject.toInternal(): MkplOp = MkplOp(
    orderNum = this.orderNum ?: "",
    title = this.title ?: "",
    ownerId = this.ownerId.toUserId(),
    amount = this.amount.toAmount(),
    opType = this.opType.fromTransport(),
    visibility = this.visibility.fromTransport(),
)

private fun OpUpdateObject.toInternal(): MkplOp = MkplOp(
    id = this.id.toOpId(),
    orderNum = this.orderNum ?: "",
    title = this.title ?: "",
    ownerId = this.ownerId.toUserId(),
    amount = this.amount.toAmount(),
    paymentId = this.paymentId.toPaymentId(),
    payment = this.payment.toAmount(),
    opType = this.opType.fromTransport(),
    visibility = this.visibility.fromTransport(),
    lock = lock.toOpLock(),
)

private fun OpVisibility?.fromTransport(): MkplVisibility = when (this) {
    OpVisibility.PUBLIC -> MkplVisibility.VISIBLE_PUBLIC
    OpVisibility.ADMIN_ONLY -> MkplVisibility.VISIBLE_TO_ADMIN
    OpVisibility.REGISTERED_ONLY -> MkplVisibility.VISIBLE_TO_REGISTERED
    null -> MkplVisibility.NONE
}

private fun PaidType?.fromTransport(): MkplPaidType = when (this) {
    PaidType.PAID -> MkplPaidType.PAID
    PaidType.UNPAID -> MkplPaidType.UNPAID
    null -> MkplPaidType.NONE
}

