package ru.otus.otuskotlin.mykotlin.app.spring.stub

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import ru.otus.otuskotlin.mykotlin.api.v1.apiV1RequestSerialize
import ru.otus.otuskotlin.mykotlin.api.v1.apiV1ResponseDeserialize
import ru.otus.otuskotlin.mykotlin.api.v1.models.*
import kotlin.test.Test

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Suppress("unused")
class OpControllerWsTest: OpControllerBaseWsTest<IRequest, IResponse>("v1") {

    @LocalServerPort
    override var port: Int = 0

    override fun deserializeRs(response: String): IResponse = apiV1ResponseDeserialize(response)
    override fun serializeRq(request: IRequest): String = apiV1RequestSerialize(request)

    @Test
    fun wsCreate(): Unit = testWsApp(
        OpCreateRequest(
        debug = OpDebug(OpRequestDebugMode.STUB, OpRequestDebugStubs.SUCCESS),
        op = OpCreateObject(
            orderNum = "001",
            title = "test1",
            opType = PaidType.UNPAID,
            visibility = OpVisibility.PUBLIC,
        )
    )
    ) { pl ->
        val mesInit = pl[0]
        val mesCreate = pl[1]
        assert(mesInit is OpInitResponse)
        assert(mesInit.result == ResponseResult.SUCCESS)
        assert(mesCreate is OpCreateResponse)
        assert(mesCreate.result == ResponseResult.SUCCESS)
    }
}