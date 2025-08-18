package ru.otus.otuskotlin.mykotlin.common.exceptions

import ru.otus.otuskotlin.mykotlin.common.models.MkplCommand


class UnknownMkplCommand(command: MkplCommand) : Throwable("Wrong command $command at mapping toTransport stage")
