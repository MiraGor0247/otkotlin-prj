package ru.otus.otuskotlin.mykotlin.app.spring.stub

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient
import ru.otus.otuskotlin.mykotlin.api.v1.mappers.*
import ru.otus.otuskotlin.mykotlin.api.v1.models.*
import ru.otus.otuskotlin.mykotlin.app.spring.config.OpConfig
import ru.otus.otuskotlin.mykotlin.app.spring.controller.OpControllerFine
import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.kotlin.MkpOpProcessor
import kotlin.test.Test
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyInserters
import org.assertj.core.api.Assertions.assertThat

@WebFluxTest(OpControllerFine::class, OpConfig::class)
internal class OpControllerTest  {
    @Autowired
    private lateinit var webClient: WebTestClient

    @Suppress("unused")
    @MockBean
    private lateinit var processor: MkpOpProcessor

    @Test
    fun createOp() = testStubOp(
        "/v1/op/create",
        OpCreateRequest(),
        MkpContext().toTransportCreate().copy(responseType = "create")
    )

    @Test
    fun readOp() = testStubOp(
        "/v1/op/read",
        OpReadRequest(),
        MkpContext().toTransportRead().copy(responseType = "read")
    )

    @Test
    fun updateOp() = testStubOp(
        "/v1/op/update",
        OpUpdateRequest(),
        MkpContext().toTransportUpdate().copy(responseType = "update")
    )

    @Test
    fun deleteOp() = testStubOp(
        "/v1/op/delete",
        OpDeleteRequest(),
        MkpContext().toTransportDelete().copy(responseType = "delete")
    )

    @Test
    fun searchOp() = testStubOp(
        "/v1/op/search",
        OpSearchRequest(),
        MkpContext().toTransportSearch().copy(responseType = "search")
    )

    private inline fun <reified Req : Any, reified Res : Any> testStubOp(
        url: String,
        requestObj: Req,
        responseObj: Res,
    ) {
        webClient
            .post()
            .uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(requestObj))
            .exchange()
            .expectStatus().isOk
            .expectBody(Res::class.java)
            .value {
                println("RESPONSE: $it")
                assertThat(it).isEqualTo(responseObj)
            }
    }
}
