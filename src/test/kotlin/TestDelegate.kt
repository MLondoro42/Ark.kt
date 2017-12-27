
import io.arkk.arkk.ApiManager
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import org.awaitility.Awaitility.*
import com.github.kittinunf.fuel.core.FuelError
import io.arkk.arkk.Voter
import io.arkk.arkk.Delegate
import java.util.concurrent.TimeUnit
import kotlin.test.fail


class TestDelegate {

    private lateinit var apiManager: ApiManager

    @Before
    fun setUp() {
        apiManager = ApiManager("https://api.arknode.net/api/")
        apiManager.updateHeader("6e84d08bd299ed97c212c886c98a57e36545c8f5d645ca7eeae63a8bd62d8988","1.0.1", 4001)
    }

    @Test
    fun testValidAkkDelegate() {
        var apiReturned        = false
        var delegate: Delegate?  = null
        var error : FuelError? = null
        apiManager.getDelegate("goose") { d, e ->
            delegate = d
            error = e
            apiReturned = true
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNotNull(delegate, "Valid Ark Delegate should not be null")
        assertNull(error, "Error should be null")
    }

    @Test
    fun testInvalidArkDelegate() {
        var apiReturned        = false
        var delegate: Delegate?  = null
        var error : FuelError? = null
        apiManager.getDelegate("ABX123DBND") { d, e ->
            delegate = d
            error = e
            apiReturned = true
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNull(delegate, "Invalid Ark Delegate should be null")
        assertNull(error, "Error should be null")
    }

    @Test
    fun testGetForgingDelegates() {
        var apiReturned        = false
        var delegates: Array<Delegate>?  = null
        var error : FuelError? = null
        apiManager.getDelegates() { d, e ->
            delegates = d
            error = e
            apiReturned = true
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNotNull(delegates, "Valid Ark Delegate should not be null")
        assertNull(error, "Error should be null")

        val count : Int = delegates?.count() ?: 0

        assertEquals(count, 51, "Delegate count should be 51, but was ${count}")
    }

    @Test
    fun testGetStandbyDelegates() {
        var apiReturned        = false
        var delegates: Array<Delegate>?  = null
        var error : FuelError? = null
        apiManager.getStandbyDelegates() { d, e ->
            delegates = d
            error = e
            apiReturned = true
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNotNull(delegates, "Valid Ark Delegate should not be null")
        assertNull(error, "Error should be null")

        val count : Int = delegates?.count() ?: 0

        assertEquals(count, 51, "Delegate count should be 51, but was ${count}")
    }

    @Test
    fun testGetDelegatesWithOffset() {
        var apiReturned    = false
        var delegates: Array<Delegate>?  = null
        var error : FuelError? = null

        apiManager.getDelegates(50, 50) {d, e ->
            delegates = d
            error = e
            apiReturned = true
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNotNull(delegates, "Valid Ark Delegate with offset should not be null")
        assertNull(error, "Error should be null")

        val count : Int = delegates?.count() ?: 0

        assertEquals(count, 50, "Delegate count should be 50, but was ${count}")
    }

    @Test
    fun testGetDelegatesWithLimit() {
        var apiReturned    = false
        var delegates: Array<Delegate>?  = null
        var error : FuelError? = null

        apiManager.getDelegates(20, 50) {d, e ->
            delegates = d
            error = e
            apiReturned = true
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNotNull(delegates, "Valid Ark Delegate with offset should not be null")
        assertNull(error, "Error should be null")

        val count : Int = delegates?.count() ?: 0

        assertEquals(count, 20, "Delegate count should be 20, but was ${count}")
    }

    @Test
    fun testVotersFromValidDelegate() {
        var apiReturned    = false
        var voters: Array<Voter>?  = null
        var error : FuelError? = null

        apiManager.getDelegate("goose") { delegate, _ ->

            if (delegate == null) fail("Delegate passed is null")

            apiManager.getVoters(delegate) { v, e ->
                voters = v
                error = e
                apiReturned = true
            }
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNotNull(voters, "Valid Forging Ark Delegate should not have null voters")
        assertNull(error, "Error should be null")
    }

    @Test
    fun testVotersFromValidPublicKey() {
        var apiReturned    = false
        var voters: Array<Voter>?  = null
        var error : FuelError? = null

        apiManager.getVoters("03c5d32dedf5441b3aafb2e0c6ad3e5568bb0b3e822807b133e2276e014d830e3c") { v, e ->
            voters = v
            error = e
            apiReturned = true
        }
        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNotNull(voters, "Valid Forging Ark Delegate should not have null voters")
        assertNull(error, "Error should be null")
    }

    @Test
    fun testVotersFromInvalidPublicKey() {
        var apiReturned    = false
        var voters: Array<Voter>?  = null
        var error : FuelError? = null

        apiManager.getVoters("03c5d32dedf5441b3aaffds8sdbasd8bb0b3e822807b133e2276e014d830e3c") { v, e ->
            voters = v
            error = e
            apiReturned = true
        }
        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNull(voters, "Invalid Forging Ark Delegate should have null voters")
        assertNull(error, "Error should be null")
    }
}