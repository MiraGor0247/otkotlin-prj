package ru.otus.otuskotlin.mykotlin.repo.tests

import ru.otus.otuskotlin.mykotlin.common.models.*

abstract class BaseInitOps(private val op: String): IInitObjects<MkpOp> {
    open val lockOld: MkpOpLock = MkpOpLock("20000000-0000-0000-0000-000000000001")
    open val lockBad: MkpOpLock = MkpOpLock("20000000-0000-0000-0000-000000000009")

    fun createInitTestModel(
        suf: String,
        ownerId: MkpUserId = MkpUserId("owner-123"),
        amount: MkpOpAmount = MkpOpAmount(1200.0),
        opType: MkpPaidType = MkpPaidType.UNPAID,
        lock: MkpOpLock = lockOld,
    ) = MkpOp(
        id = MkpOpId("ad-repo-$op-$suf"),
        orderNum = "$suf stub number",
        title = "$suf stub",
        ownerId = ownerId,
        amount = amount,
        visibility = MkpVisibility.VISIBLE_TO_REGISTERED,
        opType = opType,
        lock = lock,
    )
}