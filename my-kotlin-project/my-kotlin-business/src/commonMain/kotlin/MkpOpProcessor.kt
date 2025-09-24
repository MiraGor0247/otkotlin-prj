package ru.otus.otuskotlin.mykotlin.kotlin

import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.MkpCorSettings
import ru.otus.otuskotlin.mykotlin.common.models.*
import ru.otus.otuskotlin.mykotlin.lib.cor.rootChain
import ru.otus.otuskotlin.mykotlin.kotlin.general.initStatus
import ru.otus.otuskotlin.mykotlin.kotlin.general.operation
import ru.otus.otuskotlin.mykotlin.kotlin.general.stubs
import ru.otus.otuskotlin.mykotlin.kotlin.stubs.*
import ru.otus.otuskotlin.mykotlin.kotlin.validation.*
import ru.otus.otuskotlin.mykotlin.lib.cor.worker


class MkpOpProcessor(
    private val corSettings: MkpCorSettings = MkpCorSettings.NONE
) {
    suspend fun exec(ctx: MkpContext) = businessChain.exec(ctx.also { it.corSettings = corSettings })

    private val businessChain = rootChain<MkpContext> {
        initStatus("Инициализация статуса")

        operation("Создание заказа", MkpCommand.CREATE) {
            stubs("Обработка стабов") {
                stubCreateSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadTitle("Имитация ошибки валидации заголовка")
                stubValidationBadAmount("Имитация ошибки валидации для суммы заказа")
                stubDbError("Имитация ошибки работы с БД")
            }
            validation {
                worker("Копируем поля в opValidating") { opValidating = opRequest.deepCopy() }
                worker("Очистка id") { opValidating.id = MkpOpId.NONE }
                worker("Очистка заголовка") { opValidating.title = opValidating.title.trim() }
                validateTitleNotEmpty("Проверка, что заголовок не пуст")
                validateTitleHasContent("Проверка символов")
                validateAmountNotEmpty("Проверка, что сумма не пустая")

                finishAdValidation("Завершение проверок")
            }
        }
        operation("Получить заказ", MkpCommand.READ) {
            stubs("Обработка стабов") {
                stubReadSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadId("Имитация ошибки валидации id")
                stubDbError("Имитация ошибки работы с БД")
            }
            validation {
                worker("Копируем поля в adValidating") { opValidating = opRequest.deepCopy() }
                worker("Очистка id") { opValidating.id = MkpOpId(opValidating.id.asString().trim()) }
                validateIdNotEmpty("Проверка на непустой id")
                validateIdProperFormat("Проверка формата id")

                finishAdValidation("Успешное завершение процедуры валидации")
            }
        }
        operation("Изменить заказ", MkpCommand.UPDATE) {
            stubs("Обработка стабов") {
                stubUpdateSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadId("Имитация ошибки валидации id")
                stubValidationBadTitle("Имитация ошибки валидации заголовка")
                stubValidationBadAmount("Имитация ошибки валидации для суммы заказа")
                stubDbError("Имитация ошибки работы с БД")
            }
            validation {
                worker("Копируем поля в opValidating") { opValidating = opRequest.deepCopy() }
                worker("Очистка id") { opValidating.id = MkpOpId(opValidating.id.asString().trim()) }
                worker("Очистка заголовка") { opValidating.title = opValidating.title.trim() }
                worker("Очистка lock") { opValidating.lock = MkpOpLock(opValidating.lock.asString().trim()) }
                validateIdNotEmpty("Проверка на непустой id")
                validateIdProperFormat("Проверка формата id")
                validateTitleNotEmpty("Проверка, что заголовок не пуст")
                validateTitleHasContent("Проверка символов")
                validateAmountNotEmpty("Проверка, что сумма не пустая")
                validateLockNotEmpty("Проверка на непустой lock")
                validateLockProperFormat("Проверка формата lock")

                finishAdValidation("Завершение проверок")
            }
        }
        operation("Удалить заказ", MkpCommand.DELETE) {
            stubs("Обработка стабов") {
                stubDeleteSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadId("Имитация ошибки валидации id")
                stubDbError("Имитация ошибки работы с БД")
            }
            validation {
                worker("Копируем поля в opValidating") { opValidating = opRequest.deepCopy() }
                worker("Очистка id") { opValidating.id = MkpOpId(opValidating.id.asString().trim()) }
                worker("Очистка lock") { opValidating.lock = MkpOpLock(opValidating.lock.asString().trim()) }
                validateIdNotEmpty("Проверка на непустой id")
                validateIdProperFormat("Проверка формата id")
                validateLockNotEmpty("Проверка на непустой lock")
                validateLockProperFormat("Проверка формата lock")

                finishAdValidation("Успешное завершение процедуры валидации")
            }
        }
        operation("Поиск заказов", MkpCommand.SEARCH) {
            stubs("Обработка стабов") {
                stubSearchSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadId("Имитация ошибки валидации id")
                stubDbError("Имитация ошибки работы с БД")
            }
            validation {
                worker("Копируем поля в opFilterValidating") { opFilterValidating = opFilterRequest.deepCopy() }
                validateSearchStringLength("Валидация длины строки поиска в фильтре")

                finishAdFilterValidation("Успешное завершение процедуры валидации")
            }
        }
    }.build()
}