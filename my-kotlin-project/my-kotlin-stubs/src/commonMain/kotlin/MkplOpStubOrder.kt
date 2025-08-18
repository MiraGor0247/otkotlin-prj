package ru.otus.otuskotlin.mykotlin.stubs

import ru.otus.otuskotlin.mykotlin.common.models.*

object MkplOpStubOrder {
    val OP_ORDER1: MkplOp
        get() = MkplOp(
            id = MkplOpId("111"),
            orderNum = "13",
            title = "Kia Rio E355AA150, замена бампера",
            ownerId = MkplUserId("user-1"),
            amount = MkplOpAmount(12000.0),
            opType = MkplPaidType.UNPAID,
            visibility = MkplVisibility.VISIBLE_PUBLIC,
            lock = MkplOpLock("001"),
            permissionsClient = mutableSetOf(
                MkplOpPermissions.READ,
                MkplOpPermissions.UPDATE,
                MkplOpPermissions.DELETE,
                MkplOpPermissions.MAKE_VISIBLE_PUBLIC,
                MkplOpPermissions.MAKE_VISIBLE_REGISTERED,
                MkplOpPermissions.MAKE_VISIBLE_ADMIN,
            )
        )
}
