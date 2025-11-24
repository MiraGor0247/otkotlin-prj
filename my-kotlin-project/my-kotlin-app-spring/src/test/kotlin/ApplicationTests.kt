package ru.otus.otuskotlin.mykotlin.app.spring

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.junit.jupiter.api.Assertions.assertEquals
import ru.otus.otuskotlin.mykotlin.app.spring.config.OpConfigPostgres

@SpringBootTest
class ApplicationTests {

    @Autowired
    var pgConf: OpConfigPostgres = OpConfigPostgres()

    @Test
    fun contextLoads() {
        assertEquals(5433, pgConf.psql.port)
        assertEquals("test_db", pgConf.psql.database)
    }
}