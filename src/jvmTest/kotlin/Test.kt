import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.koinApplication
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.check.checkModules
import org.koin.test.inject
import ru.mpei.core.di.appModule
import ru.mpei.domain.repositories.KeyRepository
import ru.mpei.domain.repositories.KeyStoreRepository

class Test: KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(appModule)
    }

    private val keyRepository by inject<KeyRepository>()
    private val keyStoreRepository by inject<KeyStoreRepository>()

    @Test
    fun test() = runBlocking {
        keyStoreRepository.load()
        keyRepository.createKeyPair("dwadwa")
    }

    @Test
    fun verifyKoinApp() {

        koinApplication {
            modules(appModule)
            checkModules()
        }
    }
}