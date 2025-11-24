package ru.otus.otuskotlin.mykotlin.app.spring.repo

import org.springframework.test.web.reactive.server.WebTestClient
import ru.otus.otuskotlin.mykotlin.api.v1.mappers.*
import ru.otus.otuskotlin.mykotlin.api.v1.models.*
import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.stubs.MkpOpStub
import kotlin.test.Test
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyInserters
import org.assertj.core.api.Assertions.assertThat
import ru.otus.otuskotlin.mykotlin.common.models.*

internal abstract class OpRepoBaseTest {
    protected abstract var webClient: WebTestClient
    private val debug = OpDebug(mode = OpRequestDebugMode.TEST)
    protected val uuidNew = "10000000-0000-0000-0000-000000000001"

    @Test
    open fun createOp() = testRepoOp(
        "create",
        OpCreateRequest(
            op = MkpOpStub.get().toTransportCreate(),
            debug = debug,
        ),
        prepareCtx(MkpOpStub.prepareResult {
            id = MkpOpId(uuidNew)
            lock = MkpOpLock(uuidNew)
        })
            .toTransportCreate()
            .copy(responseType = "create")
    )

    @Test
    open fun readOp() = testRepoOp(
        "read",
        OpReadRequest(
            op = MkpOpStub.get().toTransportRead(),
            debug = debug,
        ),
        prepareCtx(MkpOpStub.get())
            .toTransportRead()
            .copy(responseType = "read")
    )

    @Test
    open fun updateOp() = testRepoOp(
        "update",
        OpUpdateRequest(
            op = MkpOpStub.prepareResult { title = "add" }.toTransportUpdate(),
            debug = debug,
        ),
        prepareCtx(MkpOpStub.prepareResult { title = "add"; lock = MkpOpLock(uuidNew) })
            .toTransportUpdate().copy(responseType = "update")
    )

    @Test
    open fun deleteOp() = testRepoOp(
        "delete",
        OpDeleteRequest(
            op = MkpOpStub.get().toTransportDelete(),
            debug = debug,
        ),
        prepareCtx(MkpOpStub.get())
            .toTransportDelete()
            .copy(responseType = "delete")
    )

    @Test
    open fun searchOp() = testRepoOp(
        "search",
        OpSearchRequest(
            opFilter = OpSearchFilter(opType = PaidType.UNPAID),
            debug = debug,
        ),
        MkpContext(
            state = MkpState.RUNNING,
            opsResponse = MkpOpStub.prepareSearchList("xx", "11", MkpPaidType.UNPAID)
                .onEach { it.permissionsClient.clear() }
                .sortedBy { it.id.asString() }
                .toMutableList()
        )
            .toTransportSearch().copy(responseType = "search")
    )


    private fun prepareCtx(op: MkpOp) = MkpContext(
        state = MkpState.RUNNING,
        opResponse = op.apply {
            // Пока не реализована эта функциональность
            permissionsClient.clear()
        },
    )

    private inline fun <reified Req : Any, reified Res : IResponse> testRepoOp(
        url: String,
        requestObj: Req,
        expectObj: Res,
    ) {
        webClient
            .post()
            .uri("/v1/ad/$url")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(requestObj))
            .exchange()
            .expectStatus().isOk
            .expectBody(Res::class.java)
            .value {
                println("RESPONSE: $it")
                val sortedResp: IResponse = when (it) {
                    is OpSearchResponse -> it.copy(ops = it.ops?.sortedBy { it.id })
                    else -> it
                }
                assertThat(sortedResp).isEqualTo(expectObj)
            }
    }
}