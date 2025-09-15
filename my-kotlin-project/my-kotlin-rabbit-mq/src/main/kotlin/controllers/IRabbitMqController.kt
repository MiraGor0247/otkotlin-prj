package ru.otus.otuskotlin.mykotlin.rabbit.controllers

import ru.otus.otuskotlin.mykotlin.rabbit.config.RabbitExchangeConfiguration

interface IRabbitMqController {
    val exchangeConfig: RabbitExchangeConfiguration
    suspend fun process()
    fun close()
}