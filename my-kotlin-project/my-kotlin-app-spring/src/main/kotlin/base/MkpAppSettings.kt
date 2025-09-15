package ru.otus.otuskotlin.mykotlin.base

import ru.otus.otuskotlin.mykotlin.common.MkpCorSettings
import ru.otus.otuskotlin.mykotlin.kotlin.MkpOpProcessor

data class MkpAppSettings(
    override val corSettings: MkpCorSettings,
    override val processor: MkpOpProcessor,
): IMkpAppSettings