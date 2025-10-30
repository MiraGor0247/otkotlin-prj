package ru.otus.otuskotlin.mykotlin.kotlin.repo

import ru.otus.otuskotlin.mykotlin.common.MkpContext
import ru.otus.otuskotlin.mykotlin.common.models.MkpWorkMode
import ru.otus.otuskotlin.mykotlin.common.repo.IRepoOp
import ru.otus.otuskotlin.mykotlin.kotlin.exceptions.MkpOpDbNotConfiguredException
import ru.otus.otuskotlin.mykotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.mykotlin.lib.cor.worker
import ru.otus.otuskotlin.mykotlin.common.helpers.*

fun ICorChainDsl<MkpContext>.initRepo(title: String) = worker {
    this.title = title
    description = """
        Вычисление основного рабочего репозитория в зависимости от зпрошенного режима работы        
    """.trimIndent()
    handle {
        adRepo = when {
            workMode == MkpWorkMode.TEST -> corSettings.repoTest
            workMode == MkpWorkMode.STUB -> corSettings.repoStub
            else -> corSettings.repoProd
        }
        if (workMode != MkpWorkMode.STUB && adRepo == IRepoOp.NONE) fail(
            errorSystem(
                violationCode = "dbNotConfigured",
                e = MkpOpDbNotConfiguredException(workMode)
            )
        )
    }
}