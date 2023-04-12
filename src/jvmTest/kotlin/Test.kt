import org.junit.Rule
import org.junit.Test
import org.koin.dsl.koinApplication
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.check.checkModules
import ru.mpei.core.di.appModule

class Test: KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(appModule)
    }

    @Test
    fun test() {
    }

    @Test
    fun verifyKoinApp() {

        koinApplication {
            modules(appModule)
            checkModules()
        }
    }
}