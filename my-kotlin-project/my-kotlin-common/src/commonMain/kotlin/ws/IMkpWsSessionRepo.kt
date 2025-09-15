package ru.otus.otuskotlin.mykotlin.common.ws

interface IMkpWsSessionRepo {
    fun add(session: IMkpWsSession)
    fun clearAll()
    fun remove(session: IMkpWsSession)
    suspend fun <K> sendAll(obj: K)

    companion object {
        val NONE = object : IMkpWsSessionRepo {
            override fun add(session: IMkpWsSession) {}
            override fun clearAll() {}
            override fun remove(session: IMkpWsSession) {}
            override suspend fun <K> sendAll(obj: K) {}
        }
    }
}
