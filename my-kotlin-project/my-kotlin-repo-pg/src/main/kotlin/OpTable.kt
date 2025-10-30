package ru.otus.otuskotlin.mykotlin.repo.pg

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.otus.otuskotlin.mykotlin.common.models.*

class OpTable(tableName: String) : Table(tableName) {
    val id = text(SqlFields.ID)
    val order_num = text(SqlFields.ORDER_NUM)
    val title = text(SqlFields.TITLE).nullable()
    val owner_id = text(SqlFields.OWNER_ID)
    val amount = double(SqlFields.AMOUNT)
    val payment_id = text(SqlFields.PAYMENT_ID).nullable()
    val payment = double(SqlFields.PAYMENT).nullable()
    val op_type = opTypeEnum(SqlFields.OP_TYPE)
    val visibility = visibilityEnum(SqlFields.VISIBILITY)
    val lock = text(SqlFields.LOCK)

    override val primaryKey = PrimaryKey(id)

    fun from(res: ResultRow) = MkpOp(
        id = MkpOpId(res[id]),
        orderNum = res[order_num] ?: "",
        title = res[title] ?: "",
        ownerId = MkpUserId(res[owner_id]),
        amount = MkpOpAmount(res[amount]),
        paymentId = MkpPaymentId(res[payment_id] ?: ""),
        payment = MkpOpAmount(res[payment] ?: 0.0),
        opType = res[op_type],
        visibility = res[visibility],
        lock = MkpOpLock(res[lock]),
    )

    fun UpdateBuilder<*>.to(op: MkpOp, randomUuid: () -> String) {
        this[id] = op.id.takeIf { it != MkpOpId.NONE }?.asString() ?: randomUuid()
        this[order_num] = op.orderNum
        this[title] = op.title
        this[owner_id] = op.ownerId.asString()
        this[amount] = op.amount.takeIf { it != MkpOpAmount.NONE}?.asDouble() ?: 0.0
        this[payment_id] = op.paymentId.asString()
        this[payment] = op.payment.takeIf { it  != MkpOpAmount.NONE}?.asDouble()
        this[op_type] = op.opType
        this[visibility] = op.visibility
        this[lock] = op.lock.takeIf { it != MkpOpLock.NONE }?.asString() ?: randomUuid()
    }
}