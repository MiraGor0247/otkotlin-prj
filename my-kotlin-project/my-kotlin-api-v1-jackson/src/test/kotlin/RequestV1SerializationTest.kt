package ru.otus.otuskotlin.mykotlin.api.v1

import ru.otus.otuskotlin.mykotlin.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestV1SerializationTest {
    private val request = OpCreateRequest(
        debug = OpDebug(
            mode = OpRequestDebugMode.STUB,
            stub = OpRequestDebugStubs.BAD_TITLE
        ),
        op = OpCreateObject(
            orderNum = "order number",
            title = "op title",
            opType = PaidType.PAID,
            visibility = OpVisibility.PUBLIC,
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(request)

        assertContains(json, Regex("\"title\":\\s*\"op title\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badTitle\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(request)
        val obj = apiV1Mapper.readValue(json, IRequest::class.java) as OpCreateRequest

        assertEquals(request, obj)
    }

    @Test
    fun deserializeNaked() {
        val jsonString = """
            {"op": null}
        """.trimIndent()
        val obj = apiV1Mapper.readValue(jsonString, OpCreateRequest::class.java)

        assertEquals(null, obj.op)
    }
}
