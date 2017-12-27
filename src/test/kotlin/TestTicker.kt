import io.arkk.arkk.ApiManager
import org.junit.Before
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import org.awaitility.Awaitility.*
import com.github.kittinunf.fuel.core.FuelError
import io.arkk.arkk.Ticker
import java.util.concurrent.TimeUnit

class TestTicker {

    private lateinit var apiManager: ApiManager

    @Before
    fun setUp() {
        apiManager = ApiManager("https://api.arknode.net/api/")
        apiManager.updateHeader("6e84d08bd299ed97c212c886c98a57e36545c8f5d645ca7eeae63a8bd62d8988", "1.0.1", 4001)
    }

    @Test
    fun testTicker() {
        var apiReturned        = false
        var ticker: Ticker?  = null
        var error : FuelError? = null

        apiManager.getTicker() { t, e ->
            ticker = t
            error = e
            apiReturned = true
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNotNull(ticker, "Ticker should not be null")
        assertNull(error, "Error should be null")
    }
}


