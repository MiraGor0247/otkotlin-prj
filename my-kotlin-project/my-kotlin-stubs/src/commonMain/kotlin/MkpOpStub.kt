package ru.otus.otuskotlin.mykotlin.stubs

import ru.otus.otuskotlin.mykotlin.common.models.MkpOp
import ru.otus.otuskotlin.mykotlin.common.models.MkpOpId
import ru.otus.otuskotlin.mykotlin.common.models.MkpPaidType
import ru.otus.otuskotlin.mykotlin.stubs.MkpOpStubOrder.OP_ORDER1

object MkpOpStub {
    fun get(): MkpOp = OP_ORDER1.copy()

    fun prepareResult(block: MkpOp.() -> Unit): MkpOp = get().apply(block)

    fun prepareSearchList(title: String, num: String, type: MkpPaidType) = listOf(
        mkpOpFilter("001",  num, title, type),
        mkpOpFilter("002",  num, title, type),
        mkpOpFilter("003",  num, title, type),
        mkpOpFilter("004",  num, title, type),
        mkpOpFilter("005",  num, title, type)
    )

    private fun mkpOpFilter(id: String, num: String, title: String, type: MkpPaidType) =
        mkpOp(OP_ORDER1, id = id, num = num, title = title, type = type)


    private fun mkpOp(base: MkpOp, id: String, num: String, title: String, type: MkpPaidType) = base.copy(
        id = MkpOpId(id),
        orderNum = num,
        title =  "order $title $num $id",
        opType = type,
    )

}
