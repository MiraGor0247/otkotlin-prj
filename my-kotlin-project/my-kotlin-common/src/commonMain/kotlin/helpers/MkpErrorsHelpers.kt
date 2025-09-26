package ru.otus.otuskotlin.mykotlin.common.helpers

import ru.otus.otuskotlin.mykotlin.LogLevel
import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.models.MkpError
import ru.otus.otuskotlin.mykotlin.common.models.MkpState

fun Throwable.asMkpError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = MkpError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)

inline fun MkpContext.addError(vararg error: MkpError) = errors.addAll(error)

inline fun MkpContext.fail(error: MkpError) {
    addError(error)
    state = MkpState.FAILING
}

inline fun errorValidation(
    field: String,
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    violationCode: String,
    description: String,
    level: LogLevel = LogLevel.ERROR,
) = MkpError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
    level = level,
)