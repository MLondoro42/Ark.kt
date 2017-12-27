import io.arkk.arkk.ApiManager
import org.junit.Before
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertEquals


class TestUtility {

    private lateinit var apiManager: ApiManager

    @Before
    fun setUp() {
        apiManager = ApiManager("https://api.arknode.net/api/")
    }

    @Test
    fun testURLManager() {
        apiManager.updateBaseURL("https://node1.arknet.cloud/api/")

        assertEquals(apiManager.baseURL, "https://node1.arknet.cloud/api/", "Manager Base URL does not match")
    }

    @Test
    fun testHTTPManager() {
        apiManager.updateURL("173.138.32.35", 4001, false)

        assertEquals(apiManager.baseURL, "http://173.138.32.35:4001/api/", "Manager Base URL does not match")
    }

    @Test
    fun testHTTPSManager() {
        apiManager.updateURL("173.138.32.35", 4001, true)

        assertEquals(apiManager.baseURL, "https://173.138.32.35:4001/api/", "Manager Base URL does not match")
    }

    @Test
    fun testHeaders() {
        apiManager.updateHeader("6e84d08bd299ed97c212c886c98a57e36545c8f5d645ca7eeae63a8bd62d8988", "1.0.1", 4001)

        assertEquals(apiManager.nodeNethash, "6e84d08bd299ed97c212c886c98a57e36545c8f5d645ca7eeae63a8bd62d8988", "Manager Nethash does not match")
        assertEquals(apiManager.nodeVersion, "1.0.1", "Manager version does not match")
        assertEquals(apiManager.nodePort, 4001, "Manager version does not match")
    }
}


