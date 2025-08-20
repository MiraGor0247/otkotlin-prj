package ru.otus.otuskotlin.mykotlin.common.exceptions

import ru.otus.otuskotlin.mykotlin.common.models.MkpCommand


class UnknownMkpCommand(command: MkpCommand) : Throwable("Wrong command $command at mapping toTransport stage")
