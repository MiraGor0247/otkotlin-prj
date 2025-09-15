package ru.otus.otuskotlin.mykotlin.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.socket.WebSocketHandler
import ru.otus.otuskotlin.mykotlin.controller.OpControllerWs

@Suppress("unused")
@Configuration
class WebSocketConfig(
    private val opController: OpControllerWs
) {
    @Bean
    fun handlerMapping(): HandlerMapping {
        val handlerMap: Map<String, WebSocketHandler> = mapOf(
            "/ws" to opController,
        )
        return SimpleUrlHandlerMapping(handlerMap, 1)
    }
}