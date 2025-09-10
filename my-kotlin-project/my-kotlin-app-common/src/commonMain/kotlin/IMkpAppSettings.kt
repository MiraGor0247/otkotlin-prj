package ru.otus.otuskotlin.mykotlin.app.common

import ru.otus.otuskotlin.mykotlin.common.MkpCorSettings
import ru.otus.otuskotlin.mykotlin.kotlin.MkpOpProcessor


interface IMkpAppSettings {
    val processor: MkpOpProcessor
    val corSettings: MkpCorSettings
}