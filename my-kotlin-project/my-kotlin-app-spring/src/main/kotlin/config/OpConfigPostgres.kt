package ru.otus.otuskotlin.mykotlin.app.spring.config

import org.springframework.boot.context.properties.ConfigurationProperties
import ru.otus.otuskotlin.mykotlin.repo.pg.SqlProperties

@ConfigurationProperties(prefix = "psql")
data class OpConfigPostgres(
    var host: String = "localhost",
    var port: Int = 5432,
    var user: String = "postgres",
    var password: String = "mykotlin-pass",
    var database: String = "mykotlin_ops",
    var schema: String = "public",
    var table: String = "ops",
) {
    val psql: SqlProperties = SqlProperties(
        host = host,
        port = port,
        user = user,
        password = password,
        database = database,
        schema = schema,
        table = table,
    )
}