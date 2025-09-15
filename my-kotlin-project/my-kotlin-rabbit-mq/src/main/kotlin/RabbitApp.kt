package ru.otus.otuskotlin.mykotlin.rabbit

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.*
import kotlinx.coroutines.launch
import ru.otus.otuskotlin.mykotlin.rabbit.config.MkpAppSettings
import ru.otus.otuskotlin.mykotlin.rabbit.controllers.IRabbitMqController
import ru.otus.otuskotlin.mykotlin.rabbit.controllers.RabbitDirectController
import java.util.concurrent.atomic.AtomicBoolean

@OptIn(ExperimentalCoroutinesApi::class)
class RabbitApp(
    appSettings: MkpAppSettings,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
) : AutoCloseable {
    private val logger = appSettings.corSettings.loggerProvider.logger(this::class)
    private val controller: IRabbitMqController = RabbitDirectController(appSettings)
    private val runFlag = AtomicBoolean(true)

    fun start() {
        runFlag.set(true)
        scope.launch(
            Dispatchers.IO.limitedParallelism(1) + CoroutineName("thread-${controller.exchangeConfig.consumerTag}")
        ) {
            while (runFlag.get()) {
                try {
                    logger.info("Process...${controller.exchangeConfig.consumerTag}")
                    controller.process()
                } catch (e: RuntimeException) {
                    // логируем, что-то делаем
                    logger.error("Обработка завалена, возможно из-за потери соединения с RabbitMQ. Рестартуем")
                    e.printStackTrace()
                }
            }
        }

    }

    override fun close() {
        runFlag.set(false)
        controller.close()
        scope.cancel()
    }
}