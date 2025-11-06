package ru.otus.otuskotlin.mykotlin.kotlin.exceptions

import ru.otus.otuskotlin.mykotlin.common.models.MkpWorkMode

class MkpOpDbNotConfiguredException (val workMode: MkpWorkMode): Exception(
    "Database is not configured properly for workmode $workMode"
)
