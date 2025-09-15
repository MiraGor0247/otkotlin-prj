package ru.otus.otuskotlin.mykotlin.controller

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.asFlux
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono
import ru.otus.otuskotlin.mykotlin.api.v1.apiV1Mapper
import ru.otus.otuskotlin.mykotlin.api.v1.models.IRequest
import ru.otus.otuskotlin.mykotlin.app.common.controllerHelper
import ru.otus.otuskotlin.mykotlin.base.MkpAppSettings
import ru.otus.otuskotlin.mykotlin.base.SpringWsSession
import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.models.MkpCommand
import ru.otus.otuskotlin.mykotlin.mappers.v1.fromTransport
import ru.otus.otuskotlin.mykotlin.mappers.v1.toTransportOp

@Component
class OpControllerWs(private val appSettings: MkpAppSettings
) : WebSocketHandler {
    private val sessions = appSettings.corSettings.wsSessions

    override fun handle(session: WebSocketSession): Mono<Void> {
        val mkpSession = SpringWsSession(session)
        sessions.add(mkpSession)
        val messageObj = flow {
            emit(process("ws-v1-init") {
                command = MkpCommand.INIT
                wsSession = mkpSession
            })
        }
        val messages = session.receive().asFlow()
            .map { message ->
                process("ws-v1-handle") {
                    wsSession = mkpSession
                    val request = apiV1Mapper.readValue(message.payloadAsText, IRequest::class.java)
                    fromTransport(request)
                }
            }

        val output = merge(messageObj, messages)
            .onCompletion {
                process("ws-v1-finish") {
                    wsSession = mkpSession
                    command = MkpCommand.FINISH
                }
                sessions.remove(mkpSession)
            }
            .map { session.textMessage(apiV1Mapper.writeValueAsString(it)) }
            .asFlux()
        return session.send(output)
    }
    private suspend fun process(logId: String, function: MkpContext.() -> Unit) = appSettings.controllerHelper(
        getRequest = function,
        toResponse = MkpContext::toTransportOp,
        clazz = this@OpControllerWs::class,
        logId = logId,
    )

}