package ru.otus.otuskotlin.mykotlin.repo.pg

data class SqlProperties(
    val host: String = "localhost",
    val port: Int = 5432,
    val user: String = "postgres",
    val password: String = "mykotlin-pass",
    val database: String = "mykotlin_ops",
    val schema: String = "public",
    val table: String = "ops",
) {
    val url: String
        get() = "jdbc:postgresql://${host}:${port}/${database}"
}