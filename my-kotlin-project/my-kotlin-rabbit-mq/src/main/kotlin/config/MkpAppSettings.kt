package ru.otus.otuskotlin.mykotlin.rabbit.config

import ru.otus.otuskotlin.mykotlin.base.IMkpAppSettings
import ru.otus.otuskotlin.mykotlin.common.MkpCorSettings
import ru.otus.otuskotlin.mykotlin.kotlin.MkpOpProcessor

data class MkpAppSettings(
    override val corSettings: MkpCorSettings = MkpCorSettings(),
    override val processor: MkpOpProcessor = MkpOpProcessor(corSettings),
    override val rabbit: RabbitConfig = RabbitConfig(),
    override val controllerConfig: RabbitExchangeConfiguration = RabbitExchangeConfiguration.NONE,
): IMkpAppSettings, IMkpAppRabbitSettings