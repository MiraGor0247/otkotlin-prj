package ru.otus.otuskotlin.mykotlin.common

import ru.otus.otuskotlin.mykotlin.MkpLoggerProvider
import ru.otus.otuskotlin.mykotlin.common.repo.IRepoOp
import ru.otus.otuskotlin.mykotlin.common.ws.IMkpWsSessionRepo

data class MkpCorSettings (
    val loggerProvider: MkpLoggerProvider = MkpLoggerProvider(),
    val wsSessions: IMkpWsSessionRepo = IMkpWsSessionRepo.NONE,
    val repoStub: IRepoOp = IRepoOp.NONE,
    val repoTest: IRepoOp = IRepoOp.NONE,
    val repoProd: IRepoOp = IRepoOp.NONE,
) {
    companion object {
        val NONE = MkpCorSettings()
    }
}