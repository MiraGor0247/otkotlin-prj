package ru.otus.otuskotlin.mykotlin.rabbit.controllers

import ru.otus.otuskotlin.mykotlin.rabbit.config.MkpAppSettings
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Delivery
import ru.otus.otuskotlin.mykotlin.api.v1.apiV1Mapper
import ru.otus.otuskotlin.mykotlin.api.v1.models.IRequest
import ru.otus.otuskotlin.mykotlin.app.common.controllerHelper
import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.logs.asMkpError
import ru.otus.otuskotlin.mykotlin.common.models.MkpState
import ru.otus.otuskotlin.mykotlin.api.v1.mappers.fromTransport
import ru.otus.otuskotlin.mykotlin.api.v1.mappers.toTransportOp

class RabbitDirectController(
    private val appSettings: MkpAppSettings,
) : RabbitProcessorBase(
    rabbitConfig = appSettings.rabbit,
    exchangeConfig = appSettings.controllerConfig,
    loggerProvider = appSettings.corSettings.loggerProvider,
) {
    override suspend fun Channel.processMessage(message: Delivery) {
        appSettings.controllerHelper(
            {
                val req = apiV1Mapper.readValue(message.body, IRequest::class.java)
                fromTransport(req)
            },
            {
                val res = toTransportOp()
                apiV1Mapper.writeValueAsBytes(res).also { msg ->
                    basicPublish(exchangeConfig.exchange, exchangeConfig.keyOut, null, msg)
                }
            },
            this@RabbitDirectController::class,
            "rabbitmq-v1-processor"
        )
    }

    override fun Channel.onError(e: Throwable, delivery: Delivery) {
        val context = MkpContext()
        e.printStackTrace()
        context.state = MkpState.FAILING
        context.errors.add(e.asMkpError())
        val response = context.toTransportOp()
        apiV1Mapper.writeValueAsBytes(response).also { msg ->
            basicPublish(exchangeConfig.exchange, exchangeConfig.keyOut, null, msg)
        }
    }
}
