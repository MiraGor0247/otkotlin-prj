package ru.otus.otuskotlin.mykotlin.stubs

import ru.otus.otuskotlin.mykotlin.common.models.*

object MkpOpStubOrder {
    val OP_ORDER1: MkpOp
        get() = MkpOp(
            id = MkpOpId("111"),
            orderNum = "13",
            title = "Kia Rio E355AA150, замена бампера",
            ownerId = MkpUserId("client-1"),
            amount = MkpOpAmount(12000.0),
            opType = MkpPaidType.UNPAID,
            visibility = MkpVisibility.VISIBLE_PUBLIC,
            lock = MkpOpLock("001"),
            permissionsClient = mutableSetOf(
                MkpOpPermissions.READ,
                MkpOpPermissions.UPDATE,
                MkpOpPermissions.DELETE,
                MkpOpPermissions.MAKE_VISIBLE_PUBLIC,
                MkpOpPermissions.MAKE_VISIBLE_REGISTERED,
                MkpOpPermissions.MAKE_VISIBLE_ADMIN,
            )
        )
}
