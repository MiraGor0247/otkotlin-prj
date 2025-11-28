
import ru.otus.otuskotlin.mykotlin.api.kmp.fromTransport
import ru.otus.otuskotlin.mykotlin.api.kmp.toTransportCreateOp
import ru.otus.otuskotlin.mykotlin.api.kmp.toTransportOp
import ru.otus.otuskotlin.mykotlin.api.v2.models.*
import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.models.*
import ru.otus.otuskotlin.mykotlin.common.stubs.MkpStubs
import ru.otus.otuskotlin.mykotlin.stubs.MkpOpStub
import kotlin.test.Test
import kotlin.test.assertEquals

class MapperTest {
    @Test
    fun fromTransport() {
        val req = OpCreateRequest(
            debug = OpDebug(
                mode = OpRequestDebugMode.STUB,
                stub = OpRequestDebugStubs.SUCCESS,
            ),
            op = MkpOpStub.get().toTransportCreateOp()
        )
        val expected = MkpOpStub.prepareResult {
            id = MkpOpId.NONE
            orderNum != ""
            amount != MkpOpAmount.NONE
            visibility = MkpVisibility.VISIBLE_PUBLIC
            lock = MkpOpLock.NONE
            permissionsClient.clear()
        }

        val context = MkpContext()
        context.fromTransport(req)

        assertEquals(MkpStubs.SUCCESS, context.stubCase)
        assertEquals(MkpWorkMode.STUB, context.workMode)
        assertEquals(expected, context.opRequest)
    }

    @Test
    fun toTransport() {
        val context = MkpContext(
            requestId = MkpRequestId("1234"),
            command = MkpCommand.CREATE,
            opResponse = MkpOpStub.get(),
            errors = mutableListOf(
                MkpError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                )
            ),
            state = MkpState.RUNNING,
        )

        val req = context.toTransportOp() as OpCreateResponse

        assertEquals(req.op, MkpOpStub.get().toTransportOp())
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("title", req.errors?.firstOrNull()?.field)
        assertEquals("wrong title", req.errors?.firstOrNull()?.message)
    }
}
