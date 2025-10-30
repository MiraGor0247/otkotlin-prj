package ru.otus.otuskotlin.mykotlin.repo.common

import ru.otus.otuskotlin.mykotlin.common.models.MkpOp
import ru.otus.otuskotlin.mykotlin.common.repo.IRepoOp

interface IRepoOpInitializable: IRepoOp {
    fun save(ads: Collection<MkpOp>) : Collection<MkpOp>
}