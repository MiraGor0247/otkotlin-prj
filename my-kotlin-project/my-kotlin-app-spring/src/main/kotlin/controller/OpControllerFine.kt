package ru.otus.otuskotlin.mykotlin.controller

import org.springframework.web.bind.annotation.*
import ru.otus.otuskotlin.mykotlin.api.v1.models.*
import ru.otus.otuskotlin.mykotlin.app.common.controllerHelper
import ru.otus.otuskotlin.mykotlin.base.MkpAppSettings
import ru.otus.otuskotlin.mykotlin.mappers.v1.*
import kotlin.reflect.KClass

@Suppress("unused")
@RestController
@RequestMapping("v1/op")
class OpControllerFine(
    private val appSettings: MkpAppSettings
) {

    @PostMapping("create")
    suspend fun create(@RequestBody request: OpCreateRequest): OpCreateResponse =
        process(appSettings, request = request, this::class, "create")

    @PostMapping("read")
    suspend fun  read(@RequestBody request: OpReadRequest): OpReadResponse =
        process(appSettings, request = request, this::class, "read")

    @RequestMapping("update", method = [RequestMethod.POST])
    suspend fun  update(@RequestBody request: OpUpdateRequest): OpUpdateResponse =
        process(appSettings, request = request, this::class, "update")

    @PostMapping("delete")
    suspend fun  delete(@RequestBody request: OpDeleteRequest): OpDeleteResponse =
        process(appSettings, request = request, this::class, "delete")

    @PostMapping("search")
    suspend fun  search(@RequestBody request: OpSearchRequest): OpSearchResponse =
        process(appSettings, request = request, this::class, "search")

    companion object {
        suspend inline fun <reified Q : IRequest, reified R : IResponse> process(
            appSettings: MkpAppSettings,
            request: Q,
            clazz: KClass<*>,
            logId: String,
        ): R = appSettings.controllerHelper(
            {
                fromTransport(request)
            },
            { toTransportOp() as R },
            clazz,
            logId,
        )
    }
}