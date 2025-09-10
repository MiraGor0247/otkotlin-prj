package ru.otus.otuskotlin.mykotlin.app.common

import kotlinx.datetime.Clock
import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.logs.asMkpError
import ru.otus.otuskotlin.mykotlin.common.models.MkpCommand
import ru.otus.otuskotlin.mykotlin.common.models.MkpState
import ru.otus.otuskotlin.mykotlin.api.log.*
import kotlin.reflect.KClass

suspend inline fun <T> IMkpAppSettings.controllerHelper(
    crossinline getRequest: suspend MkpContext.() -> Unit,
    crossinline toResponse: suspend MkpContext.() -> T,
    clazz: KClass<*>,
    logId: String,
): T {
    val logger = corSettings.loggerProvider.logger(clazz)
    val ctx = MkpContext(timeStart = Clock.System.now())
    return try {
        ctx.getRequest()
        logger.info(
            msg = "Request $logId started for ${clazz.simpleName}",
            marker = "BUSINESS",
            data = ctx.toLog(logId)
        )
        processor.exec(ctx)
        logger.info(
            msg = "Request $logId processed for ${clazz.simpleName}",
            marker = "BUSINESS",
            data = ctx.toLog(logId)
        )
        ctx.toResponse()
    } catch (e: Throwable) {
        logger.error(
            msg = "Request $logId failed for ${clazz.simpleName}",
            marker = "BUSINESS",
            data = ctx.toLog(logId),
            e = e,
        )
        ctx.state = MkpState.FAILING
        ctx.errors.add(e.asMkpError())
        processor.exec(ctx)
        if (ctx.command == MkpCommand.NONE) {
            ctx.command = MkpCommand.READ
        }
        ctx.toResponse()
    }
}
