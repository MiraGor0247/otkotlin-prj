package ru.otus.otuskotlin.mykotlin.common

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.mykotlin.common.models.*
import ru.otus.otuskotlin.mykotlin.common.repo.IRepoOp
import ru.otus.otuskotlin.mykotlin.common.stubs.MkpStubs
import ru.otus.otuskotlin.mykotlin.common.ws.IMkpWsSession

data class MkpContext(
    var command: MkpCommand = MkpCommand.NONE,
    var state: MkpState = MkpState.NONE,
    val errors: MutableList<MkpError> = mutableListOf(),

    var corSettings: MkpCorSettings = MkpCorSettings(),
    var workMode: MkpWorkMode = MkpWorkMode.PROD,
    var stubCase: MkpStubs = MkpStubs.NONE,
    var wsSession: IMkpWsSession = IMkpWsSession.NONE,

    var requestId: MkpRequestId = MkpRequestId.NONE,
    var timeStart: Instant = Instant.NONE,

    var opRequest: MkpOp = MkpOp(),
    var opFilterRequest: MkpOpFilter = MkpOpFilter(),

    var opResponse: MkpOp = MkpOp(),
    var opsResponse: MutableList<MkpOp> = mutableListOf(),

    var opValidating: MkpOp = MkpOp(),
    var opFilterValidating: MkpOpFilter = MkpOpFilter(),

    var opValidated: MkpOp = MkpOp(),
    var opFilterValidated: MkpOpFilter = MkpOpFilter(),

    var opRepo: IRepoOp = IRepoOp.NONE,
    var opRepoRead: MkpOp = MkpOp(), // То, что прочитали из репозитория
    var opRepoPrepare: MkpOp= MkpOp(), // То, что готовим для сохранения в БД
    var opRepoDone: MkpOp= MkpOp(),  // Результат, полученный из БД
    var opsRepoDone: MutableList<MkpOp> = mutableListOf(),

    )

