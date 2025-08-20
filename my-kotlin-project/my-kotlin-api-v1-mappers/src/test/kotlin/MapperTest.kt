import org.junit.Test
import ru.otus.otuskotlin.mykotlin.api.v1.models.OpCreateRequest
import ru.otus.otuskotlin.mykotlin.api.v1.models.OpCreateResponse
import ru.otus.otuskotlin.mykotlin.api.v1.models.OpDebug
import ru.otus.otuskotlin.mykotlin.api.v1.models.OpRequestDebugMode
import ru.otus.otuskotlin.mykotlin.api.v1.models.OpRequestDebugStubs
import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.models.*
import ru.otus.otuskotlin.mykotlin.common.stubs.MkpStubs
import ru.otus.otuskotlin.mykotlin.mappers.v1.fromTransport
import ru.otus.otuskotlin.mykotlin.mappers.v1.toTransportOp
import ru.otus.otuskotlin.mykotlin.mappers.v1.toTransportCreateOp
import ru.otus.otuskotlin.mykotlin.stubs.MkpOpStub
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
