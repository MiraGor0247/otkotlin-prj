package ru.otus.otuskotlin.mykotlin.rabbit.config

import ru.otus.otuskotlin.mykotlin.app.common.IMkpAppSettings

interface IMkpAppRabbitSettings: IMkpAppSettings {
    val rabbit: RabbitConfig
    val controllerConfig: RabbitExchangeConfiguration
}