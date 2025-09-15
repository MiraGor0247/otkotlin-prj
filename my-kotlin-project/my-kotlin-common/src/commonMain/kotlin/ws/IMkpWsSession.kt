package ru.otus.otuskotlin.mykotlin.common.ws

interface IMkpWsSession {
    suspend fun <T> send(obj: T)
    companion object {
        val NONE = object : IMkpWsSession {
            override suspend fun <T> send(obj: T) {

            }
        }
    }
}