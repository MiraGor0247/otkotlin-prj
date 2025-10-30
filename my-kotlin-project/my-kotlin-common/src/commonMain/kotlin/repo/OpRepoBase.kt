package ru.otus.otuskotlin.mykotlin.common.repo

import ru.otus.otuskotlin.mykotlin.common.helpers.errorSystem

abstract class OpRepoBase: IRepoOp {

    protected suspend fun tryOpMethod(block: suspend () -> IDbOpResponse) = try {
        block()
    } catch (e: Throwable) {
        DbOpResponseErr(errorSystem("methodException", e = e))
    }

    protected suspend fun tryOpsMethod(block: suspend () -> IDbOpsResponse) = try {
        block()
    } catch (e: Throwable) {
        DbOpsResponseErr(errorSystem("methodException", e = e))
    }

}
