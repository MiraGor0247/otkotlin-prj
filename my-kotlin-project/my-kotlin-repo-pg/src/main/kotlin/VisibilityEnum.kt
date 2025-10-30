package ru.otus.otuskotlin.mykotlin.repo.pg

import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject
import ru.otus.otuskotlin.mykotlin.common.models.MkpVisibility

fun Table.visibilityEnum(
    columnName: String
) = customEnumeration(
    name = columnName,
    sql = SqlFields.VISIBILITY_TYPE,
    fromDb = { value ->
        when (value.toString()) {
            SqlFields.VISIBILITY_ADMIN-> MkpVisibility.VISIBLE_TO_ADMIN
            SqlFields.VISIBILITY_REGISTERED -> MkpVisibility.VISIBLE_TO_REGISTERED
            SqlFields.VISIBILITY_PUBLIC -> MkpVisibility.VISIBLE_PUBLIC
            else -> MkpVisibility.NONE
        }
    },
    toDb = { value ->
        when (value) {
            MkpVisibility.VISIBLE_TO_ADMIN -> PgVisibilityAdmin
            MkpVisibility.VISIBLE_TO_REGISTERED -> PgVisibilityRegistered
            MkpVisibility.VISIBLE_PUBLIC -> PgVisibilityPublic
            MkpVisibility.NONE -> throw Exception("Wrong value of Visibility. NONE is unsupported")
        }
    }
)

sealed class PgVisibilityValue(eValue: String) : PGobject() {
    init {
        type = SqlFields.VISIBILITY_TYPE
        value = eValue
    }
}

object PgVisibilityPublic: PgVisibilityValue(SqlFields.VISIBILITY_PUBLIC) {
    private fun readResolve(): Any = PgVisibilityPublic
}

object PgVisibilityRegistered: PgVisibilityValue(SqlFields.VISIBILITY_REGISTERED) {
    private fun readResolve(): Any = PgVisibilityRegistered
}

object PgVisibilityAdmin: PgVisibilityValue(SqlFields.VISIBILITY_ADMIN) {
    private fun readResolve(): Any = PgVisibilityAdmin
}