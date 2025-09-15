package ru.otus.otuskotlin.mykotlin.base

import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono
import ru.otus.otuskotlin.mykotlin.api.v1.apiV1ResponseSerialize
import ru.otus.otuskotlin.mykotlin.api.v1.models.IResponse
import ru.otus.otuskotlin.mykotlin.common.ws.IMkpWsSession

data class SpringWsSession(
    private val session: WebSocketSession,
) : IMkpWsSession {
    override suspend fun <T> send(obj: T) {
        require(obj is IResponse)
        val message = apiV1ResponseSerialize(obj)
        println("SENDING to Ws: $message")
        session.send(Mono.just(session.textMessage(message)))
    }
}