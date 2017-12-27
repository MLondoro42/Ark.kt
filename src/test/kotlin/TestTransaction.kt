
import io.arkk.arkk.ApiManager
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import org.awaitility.Awaitility.*
import com.github.kittinunf.fuel.core.FuelError
import io.arkk.arkk.ArkTransaction
import java.util.concurrent.TimeUnit


class TestTransaction {

    private lateinit var apiManager: ApiManager

    @Before
    fun setUp() {
        apiManager = ApiManager("https://api.arknode.net/api/")
        apiManager.updateHeader("6e84d08bd299ed97c212c886c98a57e36545c8f5d645ca7eeae63a8bd62d8988","1.0.1", 4001)
    }

    @Test
    fun testGetValidTransaction() {
        var apiReturned        = false
        var transaction: ArkTransaction?  = null
        var error : FuelError? = null
        apiManager.getTransaction("1498c990c8787088f4f76f70e4bf9bd558161d907c47f01184534bc822f14909") { t, e ->
            transaction = t
            error = e
            apiReturned = true
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNotNull(transaction, "Valid Ark Transaction should not be null")
        assertNull(error, "Error should be null")
    }

    @Test
    fun testGetInvalidTransaction() {
        var apiReturned        = false
        var transaction: ArkTransaction?  = null
        var error : FuelError? = null
        apiManager.getTransaction("1498c990c87870dsakdasndasd907c47f01184534bc822f14909") { t, e ->
            transaction = t
            error = e
            apiReturned = true
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNull(transaction, "Invalid Ark Transaction should be null")
        assertNull(error, "Error should be null")
    }

    @Test
    fun testGetCurrentTransactions() {
        var apiReturned = false
        var transactions: Array<ArkTransaction>?  = null
        var error : FuelError? = null
        apiManager.getTransactions() { t, e ->
            transactions = t
            error = e
            apiReturned = true
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNotNull(transactions, "Fetching current Ark Transactions should not be null")
        assertNull(error, "Error should be null")
    }

    @Test
    fun testGetTransactions() {
        var apiReturned = false
        var transactions: Array<ArkTransaction>?  = null
        var error : FuelError? = null

        apiManager.getTransactions(50, 0) { t, e ->
            transactions = t
            error = e
            apiReturned = true
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNotNull(transactions, "Fetching current Ark Transactions should not be null")
        assertNull(error, "Error should be null")

        val count : Int = transactions?.count() ?: 0

        assertEquals(count, 50, "Transaction count should be 50, but was ${count}")
    }

    @Test
    fun testGetTransactionsWithLimit() {
        var apiReturned = false
        var transactions: Array<ArkTransaction>?  = null
        var error : FuelError? = null

        apiManager.getTransactions(20, 0) { t, e ->
            transactions = t
            error = e
            apiReturned = true
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNotNull(transactions, "Fetching current Ark Transactions should not be null")
        assertNull(error, "Error should be null")

        val count : Int = transactions?.count() ?: 0

        assertEquals(count, 20, "Transaction count should be 50, but was ${count}")
    }

    @Test
    fun testGetTransactionsWithOffset() {
        var apiReturned = false
        var transactions: Array<ArkTransaction>?  = null
        var error : FuelError? = null

        apiManager.getTransactions(20, 20) { t, e ->
            transactions = t
            error = e
            apiReturned = true
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNotNull(transactions, "Fetching current Ark Transactions should not be null")
        assertNull(error, "Error should be null")

        val count : Int = transactions?.count() ?: 0

        assertEquals(count, 20, "Transaction count should be 50, but was ${count}")
    }

    @Test
    fun testGetSentTransactionsFromAddress() {
        var apiReturned = false
        var transactions: Array<ArkTransaction>?  = null
        var error : FuelError? = null

        apiManager.getSentTransactions("AZse3vk8s3QEX1bqijFb21aSBeoF6vqLYE") { t, e ->
            transactions = t
            error = e
            apiReturned = true
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNotNull(transactions, "Fetching sent Ark Transactions should not be null")
        assertNull(error, "Error should be null")
    }


    @Test
    fun testGetSentTransactionsFromInvalidAddress() {
        var apiReturned = false
        var transactions: Array<ArkTransaction>?  = null
        var error : FuelError? = null

        apiManager.getSentTransactions("ZYZse3vk8s3QEX1bqijFb21aSBeoF6vqLYE") { t, e ->
            transactions = t
            error = e
            apiReturned = true
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNull(transactions, "Fetching sent Ark Transactions from invalid address should be null")
        assertNull(error, "Error should be null")
    }

    @Test
    fun testGetReceivedTransactionsFromAddress() {
        var apiReturned = false
        var transactions: Array<ArkTransaction>?  = null
        var error : FuelError? = null

        apiManager.getReceivedTransactions("AZse3vk8s3QEX1bqijFb21aSBeoF6vqLYE") { t, e ->
            transactions = t
            error = e
            apiReturned = true
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNotNull(transactions, "Fetching received Ark Transactions should not be null")
        assertNull(error, "Error should be null")
    }

    @Test
    fun testGetReceivedTransactionsFromInvalidAddress() {
        var apiReturned = false
        var transactions: Array<ArkTransaction>?  = null
        var error : FuelError? = null

        apiManager.getReceivedTransactions("ZYZse3vk8s3QEX1bqijFb21aSBeoF6vqLYE") { t, e ->
            transactions = t
            error = e
            apiReturned = true
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNull(transactions, "Fetching received Ark Transactions from invalid address should be null")
        assertNull(error, "Error should be null")
    }
}