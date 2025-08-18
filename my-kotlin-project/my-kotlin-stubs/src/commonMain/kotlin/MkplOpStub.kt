package ru.otus.otuskotlin.mykotlin.stubs

import ru.otus.otuskotlin.mykotlin.common.models.MkplOp
import ru.otus.otuskotlin.mykotlin.common.models.MkplOpId
import ru.otus.otuskotlin.mykotlin.common.models.MkplPaidType
import ru.otus.otuskotlin.mykotlin.stubs.MkplOpStubOrder.OP_ORDER1

object MkplOpStub {
    fun get(): MkplOp = OP_ORDER1.copy()

    fun prepareResult(block: MkplOp.() -> Unit): MkplOp = get().apply(block)

    fun prepareSearchList(title: String, num: String, type: MkplPaidType) = listOf(
        mkplOpFilter("001",  num, title, type),
        mkplOpFilter("002",  num, title, type),
        mkplOpFilter("003",  num, title, type),
        mkplOpFilter("004",  num, title, type),
        mkplOpFilter("005",  num, title, type)
    )

    private fun mkplOpFilter(id: String, num: String, title: String, type: MkplPaidType) =
        mkplOp(OP_ORDER1, id = id, num = num, title = title, type = type)


    private fun mkplOp(base: MkplOp, id: String, num: String,title: String, type: MkplPaidType) = base.copy(
        id = MkplOpId(id),
        orderNum = num,
        title =  "order $title $num $id",
        opType = type,
    )

}
