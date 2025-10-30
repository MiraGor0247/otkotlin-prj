package ru.otus.otuskotlin.mykotlin.repo.pg

import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject
import ru.otus.otuskotlin.mykotlin.common.models.MkpPaidType

fun Table.opTypeEnum(
    columnName: String
) = customEnumeration(
    name = columnName,
    sql = SqlFields.OP_TYPES_TYPE,
    fromDb = { value ->
        when (value.toString()) {
            SqlFields.OP_TYPES_UNPAID -> MkpPaidType.UNPAID
            SqlFields.OP_TYPES_PAID -> MkpPaidType.PAID
            else -> MkpPaidType.NONE
        }
    },
    toDb = { value ->
        when (value) {
            MkpPaidType.UNPAID -> PgOpTypeUnpaid
            MkpPaidType.PAID -> PgOpTypePaid
            MkpPaidType.NONE -> throw Exception("Wrong value of Paid Type. NONE is unsupported")
        }
    }
)

sealed class PgAdTypeValue(enVal: String): PGobject() {
    init {
        type = SqlFields.OP_TYPES_TYPE
        value = enVal
    }
}

object PgOpTypeUnpaid: PgAdTypeValue(SqlFields.OP_TYPES_UNPAID) {
    private fun readResolve(): Any = PgOpTypeUnpaid
}

object PgOpTypePaid: PgAdTypeValue(SqlFields.OP_TYPES_PAID) {
    private fun readResolve(): Any = PgOpTypePaid
}
