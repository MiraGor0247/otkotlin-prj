package ru.otus.otuskotlin.mykotlin.common

import ru.otus.otuskotlin.mykotlin.MkpLoggerProvider
import ru.otus.otuskotlin.mykotlin.common.ws.IMkpWsSessionRepo

data class MkpCorSettings (
    val loggerProvider: MkpLoggerProvider = MkpLoggerProvider(),
    val wsSessions: IMkpWsSessionRepo = IMkpWsSessionRepo.NONE,
) {
    companion object {
        val NONE = MkpCorSettings()
    }
}