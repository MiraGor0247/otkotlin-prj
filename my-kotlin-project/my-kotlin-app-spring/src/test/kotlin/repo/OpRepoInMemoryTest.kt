package ru.otus.otuskotlin.mykotlin.app.spring.repo

import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import io.mockk.slot
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.reactive.server.WebTestClient
import ru.otus.otuskotlin.mykotlin.app.spring.config.OpConfig
import ru.otus.otuskotlin.mykotlin.app.spring.controller.OpControllerFine
import ru.otus.otuskotlin.mykotlin.common.models.MkpPaidType
import ru.otus.otuskotlin.mykotlin.common.repo.DbOpFilterRequest
import ru.otus.otuskotlin.mykotlin.common.repo.DbOpIdRequest
import ru.otus.otuskotlin.mykotlin.common.repo.DbOpRequest
import ru.otus.otuskotlin.mykotlin.common.repo.IRepoOp
import ru.otus.otuskotlin.mykotlin.repo.common.OpRepoInitialized
import ru.otus.otuskotlin.mykotlin.repo.inmemory.OpRepoInMemory
import ru.otus.otuskotlin.mykotlin.stubs.MkpOpStub
import kotlin.test.Test

@WebFluxTest(
    OpControllerFine::class, OpConfig::class,
    properties = ["spring.main.allow-bean-definition-overriding=true"]
)
@Import(RepoInMemoryConfig::class)
internal class OpRepoInMemoryTest: OpRepoBaseTest() {

    @Autowired
    override lateinit var webClient: WebTestClient

    @MockkBean
    @Qualifier("testRepo")
    lateinit var testTestRepo: IRepoOp

    @BeforeEach
    fun tearUp() {
        val slotOp = slot<DbOpRequest>()
        val slotId = slot<DbOpIdRequest>()
        val slotFl = slot<DbOpFilterRequest>()
        val repo = OpRepoInitialized(
            repo = OpRepoInMemory(randomUuid = { uuidNew }),
            initObjects = MkpOpStub.prepareSearchList("xx", "", MkpPaidType.UNPAID) + MkpOpStub.get()
        )
        coEvery { testTestRepo.createOp(capture(slotOp)) } coAnswers { repo.createOp(slotOp.captured) }
        coEvery { testTestRepo.readOp(capture(slotId)) } coAnswers { repo.readOp(slotId.captured) }
        coEvery { testTestRepo.updateOP(capture(slotOp)) } coAnswers { repo.updateOP(slotOp.captured) }
        coEvery { testTestRepo.deleteOp(capture(slotId)) } coAnswers { repo.deleteOp(slotId.captured) }
        coEvery { testTestRepo.searchOp(capture(slotFl)) } coAnswers { repo.searchOp(slotFl.captured) }
    }

    @Test
    override fun createOp() = super.createOp()

    @Test
    override fun readOp() = super.readOp()

    @Test
    override fun updateOp() = super.updateOp()

    @Test
    override fun deleteOp() = super.deleteOp()

    @Test
    override fun searchOp() = super.searchOp()

}
