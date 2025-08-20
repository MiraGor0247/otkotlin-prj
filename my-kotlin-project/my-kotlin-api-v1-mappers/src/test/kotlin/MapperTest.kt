import org.junit.Test
import ru.otus.otuskotlin.mykotlin.api.v1.models.OpCreateRequest
import ru.otus.otuskotlin.mykotlin.api.v1.models.OpCreateResponse
import ru.otus.otuskotlin.mykotlin.api.v1.models.OpDebug
import ru.otus.otuskotlin.mykotlin.api.v1.models.OpRequestDebugMode
import ru.otus.otuskotlin.mykotlin.api.v1.models.OpRequestDebugStubs
import ru.otus.otuskotlin.mykotlin.api.v1.models.OpVisibility
import ru.otus.otuskotlin.mykotlin.api.v1.models.PaidType
import ru.otus.otuskotlin.mykotlin.common.MkplContext
import ru.otus.otuskotlin.mykotlin.common.models.*
import ru.otus.otuskotlin.mykotlin.common.stubs.MkplStubs
import ru.otus.otuskotlin.mykotlin.mappers.v1.fromTransport
import ru.otus.otuskotlin.mykotlin.mappers.v1.toTransportOp
import ru.otus.otuskotlin.mykotlin.mappers.v1.toTransportCreateOp
import ru.otus.otuskotlin.mykotlin.stubs.MkplOpStub
import kotlin.test.assertEquals

class MapperTest {
    @Test
    fun fromTransport() {
        val req = OpCreateRequest(
            debug = OpDebug(
                mode = OpRequestDebugMode.STUB,
                stub = OpRequestDebugStubs.SUCCESS,
            ),
            op = MkplOpStub.get().toTransportCreateOp()
        )
        val expected = MkplOpStub.prepareResult {
            id = MkplOpId.NONE
            ownerId = MkplUserId.NONE
            lock = MkplOpLock.NONE
            permissionsClient.clear()
        }

        val context = MkplContext()
        context.fromTransport(req)

        assertEquals(MkplStubs.SUCCESS, context.stubCase)
        assertEquals(MkplWorkMode.STUB, context.workMode)
        assertEquals(expected, context.opRequest)
    }

    @Test
    fun toTransport() {
        val context = MkplContext(
            requestId = MkplRequestId("1234"),
            command = MkplCommand.CREATE,
            opResponse = MkplOpStub.get(),
            errors = mutableListOf(
                MkplError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                )
            ),
            state = MkplState.RUNNING,
        )

        val req = context.toTransportOp() as OpCreateResponse

        assertEquals(req.op, MkplOpStub.get().toTransportOp())
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("title", req.errors?.firstOrNull()?.field)
        assertEquals("wrong title", req.errors?.firstOrNull()?.message)
    }
}
