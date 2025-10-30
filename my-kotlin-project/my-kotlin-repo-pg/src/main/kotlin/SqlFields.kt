package ru.otus.otuskotlin.mykotlin.repo.pg

object SqlFields {
    const val ID = "id"
    const val ORDER_NUM = "order_num"
    const val TITLE = "title"
    const val OWNER_ID = "owner_id"
    const val AMOUNT = "amount"
    const val PAYMENT_ID = "payment_id"
    const val PAYMENT = "payment"
    const val OP_TYPE = "op_type"
    const val VISIBILITY = "visibility"
    const val LOCK = "lock"
    const val LOCK_OLD = "lock_old"

    const val OP_TYPES_TYPE = "op_types_type"
    const val OP_TYPES_PAID = "paid"
    const val OP_TYPES_UNPAID = "unpaid"

    const val VISIBILITY_TYPE = "op_visibilities_type"
    const val VISIBILITY_PUBLIC = "public"
    const val VISIBILITY_ADMIN = "admin"
    const val VISIBILITY_REGISTERED = "registered"

    const val FILTER_TITLE = TITLE
    const val FILTER_OWNER_ID = OWNER_ID
    const val FILTER_OP_TYPE = OP_TYPE

    const val DELETE_OK = "DELETE_OK"

    fun String.quoted() = "\"$this\""
    val allFields = listOf(
        ID, ORDER_NUM, TITLE, OWNER_ID, AMOUNT, PAYMENT_ID, PAYMENT, OP_TYPE, VISIBILITY, LOCK,
    )
}