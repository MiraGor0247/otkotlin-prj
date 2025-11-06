package ru.otus.otuskotlin.mykotlin.repo.common

import ru.otus.otuskotlin.mykotlin.common.models.MkpOp

/**
 * Делегат для всех репозиториев, позволяющий инициализировать базу данных предзагруженными данными
 */
class OpRepoInitialized(
    val repo: IRepoOpInitializable,
    initObjects: Collection<MkpOp> = emptyList(),
) : IRepoOpInitializable by repo {
    @Suppress("unused")
    val initializedObjects: List<MkpOp> = save(initObjects).toList()
}