import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlinx.serialization.encodeToString
import ru.otus.otuskotlin.mykotlin.api.kmp.apiV2Mapper
import ru.otus.otuskotlin.mykotlin.api.v2.models.*

class RequestV2SerializationTest {
    private val request: IRequest = OpCreateRequest(
        debug = OpDebug(
            mode = OpRequestDebugMode.STUB,
            stub = OpRequestDebugStubs.BAD_TITLE
        ),
        op = OpCreateObject(
            orderNum = "12",
            title = "op title",
            opType = PaidType.UNPAID,
            visibility = OpVisibility.PUBLIC,
        )
    )

    @Test
    fun serialize() {
        val json = apiV2Mapper.encodeToString(IRequest.serializer(), request)

        println(json)

        assertContains(json, Regex("\"title\":\\s*\"op title\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badTitle\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV2Mapper.encodeToString(request)
        val obj = apiV2Mapper.decodeFromString<IRequest>(json) as OpCreateRequest

        assertEquals(request, obj)
    }

    @Test
    fun deserializeNaked() {
        val jsonString = """
            {"op": null}
        """.trimIndent()
        val obj = apiV2Mapper.decodeFromString<OpCreateRequest>(jsonString)

        assertEquals(null, obj.op)
    }
}
