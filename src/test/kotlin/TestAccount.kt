
import io.arkk.arkk.ApiManager
import org.junit.Before
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import org.awaitility.Awaitility.*
import com.github.kittinunf.fuel.core.FuelError
import io.arkk.arkk.Account
import io.arkk.arkk.Delegate
import java.util.concurrent.TimeUnit


class TestAccount {

    private lateinit var apiManager: ApiManager

    

    @Before
    fun setUp() {
        apiManager = ApiManager("https://api.arknode.net/api/")
        apiManager.updateHeader("6e84d08bd299ed97c212c886c98a57e36545c8f5d645ca7eeae63a8bd62d8988","1.0.1", 4001)
    }

    @Test
    fun testValidArkAddress() {
        var apiReturned        = false
        var account: Account?  = null
        var error : FuelError? = null
        apiManager.getAccount("Adu61K21jCvCyRcTNcUJHoYLUFpLfNX1vS") { a, e ->
            account = a
            error = e
            apiReturned = true
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNotNull(account, "Valid Ark Address should not be null")
        assertNull(error, "Error should be null")

    }

    @Test
    fun testInvalidArkAddress() {
        var apiReturned        = false
        var account: Account?  = null
        var error : FuelError? = null
        apiManager.getAccount("ZYu61K21jCvCyRcTNcUJHoYLUFpLfNX1vS") { a, e ->
            account = a
            error = e
            apiReturned = true
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNull(account, "Decoding Invalid Ark Address should not return a valid `Account`")
        assertNull(error, "Error should be null")
    }

    @Test
    fun testValidArkAddressBalance() {
        var apiReturned        = false
        var balance: Double?  = null
        var error : FuelError? = null
        apiManager.getBalance("Adu61K21jCvCyRcTNcUJHoYLUFpLfNX1vS") { b, e ->
            balance = b
            error = e
            apiReturned = true
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNotNull(balance, "Valid Ark Address should not have null `Balance`")
        assertNull(error, "Error should be null")
    }

    @Test
    fun testInvalidArkAddressBalance() {
        var apiReturned        = false
        var balance: Double?  = null
        var error : FuelError? = null
        apiManager.getBalance("ZYu61K21jCvCyRcTNcUJHoYLUFpLfNX1vS") { b, e ->
            balance = b
            error = e
            apiReturned = true
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNull(balance, "Decoding Invalid Ark Address should not return a valid account `Balance`")
        assertNull(error, "Error should be null")
    }

    @Test
    fun testValidArkAddressVote() {
        var apiReturned        = false
        var delegate: Delegate?  = null
        var error : FuelError? = null
        apiManager.getVote("Aasu14aTs9ipZdy1FMv7ay1Vqn3jPskA8t") { d, e ->
            delegate = d
            error = e
            apiReturned = true
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNotNull(delegate, "Valid Ark Address should not have null delegate (Relies on delegate Jarunik from having Vote)")
        assertNull(error, "Error should be null")
    }

    @Test
    fun testInvalidArkAddressVote() {
        var apiReturned        = false
        var delegate: Delegate?  = null
        var error : FuelError? = null
        apiManager.getVote("ZYu61K21jCvCyRcTNcUJHoYLUFpLfNX1vS") { d, e ->
            delegate = d
            error = e
            apiReturned = true
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNull(delegate, "Decoding Invalid Ark Address should not return a valid `Delegate`")
        assertNull(error, "Error should be null")
    }

    /// Test for Valid Ark Address where no vote has been cast
    /// Bittrex Ark Address should not have any votes
    @Test
    fun testValidArkAddressEmptyVote() {
        var apiReturned        = false
        var delegate: Delegate?  = null
        var error : FuelError? = null
        apiManager.getVote("AUexKjGtgsSpVzPLs6jNMM6vJ6znEVTQWK") { d, e ->
            delegate = d
            error = e
            apiReturned = true
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNull(delegate, "Bittrex Ark Address should not return delegate vote")
        assertNull(error, "Error should be null")

    }

    fun testGetPublicKey() {
        var apiReturned        = false
        var publicKey: String?  = null
        var error : FuelError? = null
        apiManager.getPublicKey("Adu61K21jCvCyRcTNcUJHoYLUFpLfNX1vS") { p, e ->
            publicKey = p
            error = e
            apiReturned = true
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNotNull(publicKey, "Valid Ark Address should not have null `PublicKey`")
        assertNull(error, "Decoding Valid Ark Address should not throw error")
        assertNull(error, "Error should be null")
    }
}
