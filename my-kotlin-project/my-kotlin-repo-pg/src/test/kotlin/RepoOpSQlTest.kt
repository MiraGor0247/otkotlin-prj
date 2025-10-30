package ru.otus.otuskotlin.mykotlin.repo.pg

import com.benasher44.uuid.uuid4
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.testcontainers.containers.ComposeContainer
import org.testcontainers.containers.wait.strategy.Wait
import ru.otus.otuskotlin.mykotlin.common.models.MkpOp
import ru.otus.otuskotlin.mykotlin.repo.common.IRepoOpInitializable
import ru.otus.otuskotlin.mykotlin.repo.common.OpRepoInitialized
import ru.otus.otuskotlin.mykotlin.repo.tests.*
import java.io.File
import java.time.Duration
import kotlin.test.AfterTest
import kotlin.test.Ignore

private fun IRepoOpInitializable.clear() {
    val pgRepo = (this as OpRepoInitialized).repo as RepoOpSql
    pgRepo.clear()
}

@RunWith(Enclosed::class)
class RepoOpSQlTest {
    class RepoOpSQLCreateTest : RepoOpCreateTest() {
        override val repo = repoUnderTestContainer(
            initObjects,
            randomUuid = { uuidNew.asString() },
        )

        @AfterTest
        fun tearDown() = repo.clear()
    }

    class RepoOpSQLReadTest : RepoOpReadTest() {
        override val repo = repoUnderTestContainer(initObjects)

        @AfterTest
        fun tearDown() = repo.clear()
    }

    class RepoOpSQLUpdateTest : RepoOpUpdateTest() {
        override val repo = repoUnderTestContainer(
            initObjects,
            randomUuid = { lockNew.asString() },
        )

        @AfterTest
        fun tearDown() = repo.clear()
    }

    class RepoOpSQLDeleteTest : RepoOpDeleteTest() {
        override val repo = repoUnderTestContainer(initObjects)

        @AfterTest
        fun tearDown() = repo.clear()
    }

    class RepoOpSQLSearchTest : RepoOpSearchTest() {
        override val repo = repoUnderTestContainer(initObjects)

        @AfterTest
        fun tearDown() = repo.clear()
    }

    @Ignore
    companion object {
        private const val PG_SERVICE = "psql"
        private const val MG_SERVICE = "liquibase"

        private val container: ComposeContainer by lazy {
            val res = this::class.java.classLoader.getResource("docker-compose-pg.yml")
                ?: throw Exception("No resource found")
            val file = File(res.toURI())
            ComposeContainer(
                file,
            )
                .withExposedService(PG_SERVICE, 5432)
                .withStartupTimeout(Duration.ofSeconds(3000))
                .waitingFor(
                    MG_SERVICE,
                    Wait.forLogMessage(".*Liquibase command 'update' was executed successfully.*", 1)
                )
        }
        private const val HOST = "localhost"
        private const val USER = "postgres"
        private const val PASS = "mykotlin-pass"
        private val PORT by lazy {
            container.getServicePort(PG_SERVICE, 5432) ?: 5432
        }

        fun repoUnderTestContainer(
            initObjects: Collection<MkpOp> = emptyList(),
            randomUuid: () -> String = { uuid4().toString() },
        ): IRepoOpInitializable = OpRepoInitialized(
            repo = RepoOpSql(
                SqlProperties(
                    host = HOST,
                    user = USER,
                    password = PASS,
                    port = PORT,
                ),
                randomUuid = randomUuid
            ),
            initObjects = initObjects,
        )

        @JvmStatic
        @BeforeClass
        fun start() {
            container.start()
        }

        @JvmStatic
        @AfterClass
        fun finish() {
            container.stop()
        }
    }

}