import io.arkk.arkk.ApiManager
import org.junit.Before
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import org.awaitility.Awaitility.*
import com.github.kittinunf.fuel.core.FuelError
import io.arkk.arkk.Block
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import kotlin.test.fail


class TestBlock {

    private lateinit var apiManager: ApiManager

    @Before
    fun setUp() {
        apiManager = ApiManager("https://api.arknode.net/api/")
        apiManager.updateHeader("6e84d08bd299ed97c212c886c98a57e36545c8f5d645ca7eeae63a8bd62d8988", "1.0.1", 4001)
    }

    @Test
    fun testGetValidBlock() {
        var apiReturned    = false
        var block: Block?  = null
        var error : FuelError? = null
        apiManager.getBlock("9139882080805628195") { b, e ->
            block = b
            error = e
            apiReturned = true
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNotNull(block, "Valid Ark Block Address should not be null")
        assertNull(error, "Error should be null")
    }

    @Test
    fun testGetInvalidBlock() {
        var apiReturned    = false
        var block: Block?  = null
        var error : FuelError? = null
        apiManager.getBlock("913988208080562819524123") { b, e ->
            block = b
            error = e
            apiReturned = true
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNull(block, "Invalid Ark Block Address should be null")
        assertNull(error, "Error should be null")
    }

    @Test
    fun testGetCurrentBlocks() {
        var apiReturned    = false
        var blocks: Array<Block>?  = null
        var error : FuelError? = null
        apiManager.getBlocks() { b, e ->
            blocks = b
            error = e
            apiReturned = true
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNotNull(blocks, "Fetching current blocks should not be null")
        assertNull(error, "Error should be null")
    }

    @Test
    fun testBlocksFromValidDelegate() {
        var apiReturned    = false
        var blocks: Array<Block>?  = null
        var error : FuelError? = null

        apiManager.getDelegate("goose") { delegate, _ ->

            if (delegate == null) fail("Delegate passed is null")

            apiManager.getBlocks(delegate, 50, 0) { b, e ->
                blocks = b
                error = e
                apiReturned = true
            }
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNotNull(blocks, "Valid Forging Ark Delegate should not have null blocks")
        assertNull(error, "Error should be null")
    }

    @Test
    fun testBlocksFromValidPublicKey() {
        var apiReturned    = false
        var blocks: Array<Block>?  = null
        var error : FuelError? = null

        apiManager.getBlocks("03c5d32dedf5441b3aafb2e0c6ad3e5568bb0b3e822807b133e2276e014d830e3c" ,50, 0 ) { b, e ->
            blocks = b
            error = e
            apiReturned = true
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNotNull(blocks, "Valid Forging Ark Delegate should not have null blocks")
        assertNull(error, "Error should be null")
    }

    @Test
    fun testBlocksFromInvalidPublicKey() {
        var apiReturned    = false
        var blocks: Array<Block>?  = null
        var error : FuelError? = null

        apiManager.getBlocks("03c5d32dedf5441b3aafb2e0c6ad3e556fsafnasjasdfmn014d830e3c" ,50, 0 ) { b, e ->
            blocks = b
            error = e
            apiReturned = true
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNull(blocks, "Invalid publicKey should not return blocks")
        assertNull(error, "Error should be null")
    }

    @Test
    fun testBlocksFromValidPublicKeyWithOffset() {
        var apiReturned    = false
        var blocks: Array<Block>?  = null
        var error : FuelError? = null

        apiManager.getBlocks("03c5d32dedf5441b3aafb2e0c6ad3e5568bb0b3e822807b133e2276e014d830e3c" ,50, 50 ) { b, e ->
            blocks = b
            error = e
            apiReturned = true
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNotNull(blocks, "Valid Forging Ark Delegate should not have null blocks")
        assertNull(error, "Error should be null")
    }

    @Test
    fun testBlocksFromValidPublicKeyWithLimit() {
        var apiReturned    = false
        var blocks: Array<Block>?  = null
        var error : FuelError? = null

        apiManager.getBlocks("03c5d32dedf5441b3aafb2e0c6ad3e5568bb0b3e822807b133e2276e014d830e3c" ,20, 0 ) { b, e ->
            blocks = b
            error = e
            apiReturned = true
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNotNull(blocks, "Valid Forging Ark Delegate should not have null blocks")
        assertNull(error, "Error should be null")

        val count : Int = blocks?.count() ?: 0

        assertEquals(count, 20, "Block count should be 20, but was ${count}")
    }

    @Test
    fun testGetLastBlock() {
        var apiReturned    = false
        var block: Block?  = null
        var error : FuelError? = null
        apiManager.getLastBlock() { b, e ->
            block = b
            error = e
            apiReturned = true
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNotNull(block, "Fetching last block should not be null")
        assertNull(error, "Error should be null")
    }}