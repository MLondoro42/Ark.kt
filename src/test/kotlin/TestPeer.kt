import io.arkk.arkk.ApiManager
import org.junit.Before
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import org.awaitility.Awaitility.*
import com.github.kittinunf.fuel.core.FuelError
import io.arkk.arkk.Peer
import io.arkk.arkk.PeerVersion
import java.util.concurrent.TimeUnit

class TestPeer {

    private lateinit var apiManager: ApiManager

    @Before
    fun setUp() {
        apiManager = ApiManager("https://api.arknode.net/api/")
        apiManager.updateHeader("6e84d08bd299ed97c212c886c98a57e36545c8f5d645ca7eeae63a8bd62d8988", "1.0.1", 4001)
    }

    @Test
    fun testGetPeers() {
        var apiReturned        = false
        var peers: Array<Peer>?  = null
        var error : FuelError? = null
        apiManager.getPeers() { p, e ->
            peers = p
            error = e
            apiReturned = true
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNotNull(peers, "Ark Peers should not be null")
        assertNull(error, "Error should be null")
    }

    @Test
    fun testValidPeer() {
        var apiReturned = false
        var peer: Peer?  = null
        var error : FuelError? = null
        apiManager.getPeer("174.138.32.35", 4001) { p, e ->
            peer = p
            error = e
            apiReturned = true
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNotNull(peer, "Valid Ark Peer should not be null")
        assertNull(error, "Error should be null")
    }

    @Test
    fun testInvalidPeer() {
        var apiReturned = false
        var peer: Peer?  = null
        var error : FuelError? = null
        apiManager.getPeer("173.138.32.35", 4001) { p, e ->
            peer = p
            error = e
            apiReturned = true
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNull(peer, "Invalid Ark Peer should be null")
        assertNull(error, "Error should be null")
    }

    @Test
    fun testPeerVersion() {
        var apiReturned = false
        var peerversion: PeerVersion?  = null
        var error : FuelError? = null
        apiManager.getPeerVersion() { p, e ->
            peerversion = p
            error = e
            apiReturned = true
        }

        await().atMost(10, TimeUnit.SECONDS).until { apiReturned == true }

        assertNotNull(peerversion, "Peerversion should not be null")
        assertNull(error, "Error should be null")
    }
}


