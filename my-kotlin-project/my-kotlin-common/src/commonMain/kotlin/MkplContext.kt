package ru.otus.otuskotlin.mykotlin.common

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.mykotlin.common.models.*
import ru.otus.otuskotlin.mykotlin.common.stubs.MkplStubs

data class MkplContext(
    var command: MkplCommand = MkplCommand.NONE,
    var state: MkplState = MkplState.NONE,
    val errors: MutableList<MkplError> = mutableListOf(),

    var workMode: MkplWorkMode = MkplWorkMode.PROD,
    var stubCase: MkplStubs = MkplStubs.NONE,

    var requestId: MkplRequestId = MkplRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var opRequest: MkplOp = MkplOp(),
    var opFilterRequest: MkplOpFilter = MkplOpFilter(),

    var opResponse: MkplOp = MkplOp(),
    var opsResponse: MutableList<MkplOp> = mutableListOf(),

    )
