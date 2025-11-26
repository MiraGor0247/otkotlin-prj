package ru.otus.otuskotlin.mykotlin.app.common

import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.mykotlin.api.v1.mappers.fromTransport
import ru.otus.otuskotlin.mykotlin.api.v1.mappers.toTransportOp
import ru.otus.otuskotlin.mykotlin.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertEquals
import ru.otus.otuskotlin.mykotlin.common.MkpCorSettings
import ru.otus.otuskotlin.mykotlin.kotlin.MkpOpProcessor

class ControllerTest {
    private val request = OpCreateRequest(
        op = OpCreateObject(
            orderNum = "0011",
            title = "order 11",
            amount = 1000.0,
            opType = PaidType.UNPAID,
            visibility = OpVisibility.PUBLIC,
        ),
        debug = OpDebug(mode = OpRequestDebugMode.STUB, stub = OpRequestDebugStubs.SUCCESS)
    )
    private val appSettings: IMkpAppSettings = object : IMkpAppSettings {
        override val corSettings: MkpCorSettings = MkpCorSettings()
        override val processor: MkpOpProcessor = MkpOpProcessor(corSettings)
    }

    private suspend fun createOpSpring(request: OpCreateRequest): OpCreateResponse =
        appSettings.controllerHelper(
            { fromTransport(request) },
            { toTransportOp() as OpCreateResponse },
            ControllerTest::class,
            "controller-test"
        )

    @Test
    fun springHelperTest() = runTest {
        val res = createOpSpring(request)
        assertEquals(ResponseResult.SUCCESS, res.result)
    }
}