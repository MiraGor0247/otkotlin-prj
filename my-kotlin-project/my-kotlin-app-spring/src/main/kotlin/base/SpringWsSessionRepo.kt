package ru.otus.otuskotlin.mykotlin.base

import ru.otus.otuskotlin.mykotlin.common.ws.IMkpWsSession
import ru.otus.otuskotlin.mykotlin.common.ws.IMkpWsSessionRepo

class SpringWsSessionRepo: IMkpWsSessionRepo {
    private val sessions: MutableSet<IMkpWsSession> = mutableSetOf()
    override fun add(session: IMkpWsSession) {
        sessions.add(session)
    }

    override fun clearAll() {
        sessions.clear()
    }

    override fun remove(session: IMkpWsSession) {
        sessions.remove(session)
    }

    override suspend fun <T> sendAll(obj: T) {
        sessions.forEach { it.send(obj) }
    }
}