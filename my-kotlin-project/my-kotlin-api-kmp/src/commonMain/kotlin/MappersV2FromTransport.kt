package ru.otus.otuskotlin.mykotlin.api.kmp

import ru.otus.otuskotlin.mykotlin.api.v2.models.*
import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.models.*
import ru.otus.otuskotlin.mykotlin.common.models.MkpWorkMode
import ru.otus.otuskotlin.mykotlin.common.stubs.MkpStubs

fun MkpContext.fromTransport(request: IRequest) = when (request) {
    is OpCreateRequest -> fromTransport(request)
    is OpReadRequest -> fromTransport(request)
    is OpUpdateRequest -> fromTransport(request)
    is OpDeleteRequest -> fromTransport(request)
    is OpSearchRequest -> fromTransport(request)
}

private fun String?.toOpId() = this?.let { MkpOpId(it) } ?: MkpOpId.NONE
private fun String?.toOpWithId() = MkpOp(id = this.toOpId())
private fun String?.toOpLock() = this?.let { MkpOpLock(it) } ?: MkpOpLock.NONE
private fun String?.toUserId() = this?.let { MkpUserId(it) } ?: MkpUserId.NONE
private fun Double?.toAmount() = this?.let { MkpOpAmount(it) } ?: MkpOpAmount.NONE
private fun String?.toPaymentId() = this?.let { MkpPaymentId(it) } ?: MkpPaymentId.NONE

private fun OpDebug?.transportToWorkMode(): MkpWorkMode = when (this?.mode) {
    OpRequestDebugMode.PROD -> MkpWorkMode.PROD
    OpRequestDebugMode.TEST -> MkpWorkMode.TEST
    OpRequestDebugMode.STUB -> MkpWorkMode.STUB
    null -> MkpWorkMode.PROD
}

private fun OpDebug?.transportToStubCase(): MkpStubs = when (this?.stub) {
    OpRequestDebugStubs.SUCCESS -> MkpStubs.SUCCESS
    OpRequestDebugStubs.NOT_FOUND -> MkpStubs.NOT_FOUND
    OpRequestDebugStubs.BAD_ID -> MkpStubs.BAD_ID
    OpRequestDebugStubs.BAD_TITLE -> MkpStubs.BAD_TITLE
    OpRequestDebugStubs.BAD_NUMBER -> MkpStubs.BAD_NUMBER
    OpRequestDebugStubs.BAD_VISIBILITY -> MkpStubs.BAD_VISIBILITY
    OpRequestDebugStubs.CANNOT_DELETE -> MkpStubs.CANNOT_DELETE
    OpRequestDebugStubs.BAD_SEARCH_STRING -> MkpStubs.BAD_SEARCH_STRING
    null -> MkpStubs.NONE
}

fun MkpContext.fromTransport(request: OpCreateRequest) {
    command = MkpCommand.CREATE
    opRequest = request.op?.toInternal() ?: MkpOp()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun MkpContext.fromTransport(request: OpReadRequest) {
    command = MkpCommand.READ
    opRequest = request.op.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun OpReadObject?.toInternal(): MkpOp = if (this != null) {
    MkpOp(id = id.toOpId())
} else {
    MkpOp()
}


fun MkpContext.fromTransport(request: OpUpdateRequest) {
    command = MkpCommand.UPDATE
    opRequest = request.op?.toInternal() ?: MkpOp()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun MkpContext.fromTransport(request: OpDeleteRequest) {
    command = MkpCommand.DELETE
    opRequest = request.op.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun OpDeleteObject?.toInternal(): MkpOp = if (this != null) {
    MkpOp(
        id = id.toOpId(),
        lock = lock.toOpLock(),
    )
} else {
    MkpOp()
}

fun MkpContext.fromTransport(request: OpSearchRequest) {
    command = MkpCommand.SEARCH
    opFilterRequest = request.opFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}


private fun OpSearchFilter?.toInternal(): MkpOpFilter = MkpOpFilter(
    searchString = this?.searchString ?: ""
)

private fun OpCreateObject.toInternal(): MkpOp = MkpOp(
    orderNum = this.orderNum ?: "",
    title = this.title ?: "",
    ownerId = this.ownerId.toUserId(),
    amount = this.amount.toAmount(),
    opType = this.opType.fromTransport(),
    visibility = this.visibility.fromTransport(),
)

private fun OpUpdateObject.toInternal(): MkpOp = MkpOp(
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

private fun OpVisibility?.fromTransport(): MkpVisibility = when (this) {
    OpVisibility.PUBLIC -> MkpVisibility.VISIBLE_PUBLIC
    OpVisibility.ADMIN_ONLY -> MkpVisibility.VISIBLE_TO_ADMIN
    OpVisibility.REGISTERED_ONLY -> MkpVisibility.VISIBLE_TO_REGISTERED
    null -> MkpVisibility.NONE
}

private fun PaidType?.fromTransport(): MkpPaidType = when (this) {
    PaidType.PAID -> MkpPaidType.PAID
    PaidType.UNPAID -> MkpPaidType.UNPAID
    null -> MkpPaidType.NONE
}

