
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import ru.otus.otuskotlin.mykotlin.api.v1.models.*
import ru.otus.otuskotlin.mykotlin.app.common.IMkpAppSettings
import ru.otus.otuskotlin.mykotlin.app.common.controllerHelper
import ru.otus.otuskotlin.mykotlin.common.MkpCorSettings
import ru.otus.otuskotlin.mykotlin.common.models.MkpOpAmount
import ru.otus.otuskotlin.mykotlin.kotlin.MkpOpProcessor
import ru.otus.otuskotlin.mykotlin.mappers.v1.fromTransport
import ru.otus.otuskotlin.mykotlin.mappers.v1.toTransportOp

class ControllerTest {
    private val request = OpCreateRequest(
        op = OpCreateObject(
            orderNum = "0011",
            title = "order 11",
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
            "controller-v2-test"
        )

    class TestApplicationCall(private val request: IRequest) {
        var res: IResponse? = null

        @Suppress("UNCHECKED_CAST")
        fun <T : IRequest> receive(): T = request as T
        fun respond(res: IResponse) {
            this.res = res
        }
    }

    @Test
    fun springHelperTest() = runTest {
        val res = createOpSpring(request)
        assertEquals(ResponseResult.SUCCESS, res.result)
    }
}