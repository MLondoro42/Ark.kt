package io.arkk.arkk

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.core.response
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.github.kittinunf.result.getAs
import io.ark.core.Transaction


/**
 *
 * Public ApiManager Class for accessing Ark API
 *
 */
public class ApiManager {

    // URL Manager

    /**
     *
     * The base URL used to access the Ark API
     *
     */
    public var baseURL : String = ""
        private set


    /**
     *
     * A valid ark-node nethash
     *
     *  Example: "6e84d08bd299ed97c212c886c98a57e36545c8f5d645ca7eeae63a8bd62d8988"
     *
     */
    public var nodeNethash : String? = null
        private set

    /**
     *
     * A valid ark-node version
     *
     *  Example: "1.0.1"
     *
     */
    public var nodeVersion : String? = null
        private set

    /**
     *
     * A valid server port
     *
     *  Example: 4001
     *
     */
    private var nodePort : Int? = null
        private set


    /**
     *
     * Public constructor for the APIManagr Object
     * Sets the base URL to the specifided URL
     *
     * Example URL: "https://api.arknode.net/api/"
     *
     */
    constructor(url : String) {
        updateBaseURL(url)
        setNetworkPreferences()
    }

    /**
     *
     * Public constructor for the APIManagr Object
     * Sets the base URL with a custom node address
     *
     * Example URL: "http://78.229.106.139:4001"
     *
     */
    constructor(ipAddress: String, port: Int, ssl: Boolean) {
        updateURL(ipAddress, port, ssl)
        setNetworkPreferences()
    }

    /**
     * Public accessor method for updating the base url
     *
     * Example URL: "https://api.arknode.net/api/"
     *
     * @param url the new base url
     *
     */
    public fun updateBaseURL(url: String) {
        baseURL = url
        setNetworkPreferences()
    }

    /**
     * Public accessor method for updating the base url with a custom node address
     *
     * Example URL: "http://78.229.106.139:4001"
     *
     * @param ipAddress ip address of the Ark node
     * @param port A valid ark-node version. Default production is `4001`
     * @param ssl Sets whether SSL should be used
     *
     */
    public  fun updateURL(ipAddress: String, port: Int, ssl: Boolean) {
        if (ssl == true) {
            baseURL = "https://" + ipAddress + ":" + port
        } else {
            baseURL = "http://" + ipAddress + ":" + port
        }
        setNetworkPreferences()
    }

    /**
     * Public accessor method for updating the node headers
     *
     *  Example nethash: "6e84d08bd299ed97c212c886c98a57e36545c8f5d645ca7eeae63a8bd62d8988"
     *  Example version: "1.0.1"
     *  Example nethash: 4001
     *
     * @param ipAddress A valid ark-node nethash
     * @param version A valid ark-node version. Default production is `1.0.1`
     * @param port A valid server port. Default production is `4001`
     *
     */
    public fun updateHeader(nethash: String, version: String, port: Int) {
        nodeNethash = nethash
        nodeVersion = version
        nodePort    = port
        FuelManager.instance.baseHeaders = mapOf(nethash to "nethash", version to "version", port.toString() to "port")
    }

    // Account Info


    /**
     * Public accessor method for fetching an Ark Address
     *
     * Example Ark Account:
     * "account": {"address": "Adu61K21jCvCyRcTNcUJHoYLUFpLfNX1vS",
     * "unconfirmedBalance": "6195626142833",
     * "balance": "6195626142833",
     * "publicKey": "02e0ab3b592463e5cf780f614a722b2355a9145efc33ce415dbda32011504c7c19",
     * "unconfirmedSignature": 0,
     * "secondSignature": 0,
     * "secondPublicKey": null,
     * "multisignatures": [],
     * "u_multisignatures": []}
     *
     * @param address ip address of the Ark node
     * @param callback The callback returned after the network request. Contains an optional Ark `Account` and `FuelErrror`
     *
     */
    public fun getAccount(address: String, callback: (account: Account?, error: FuelError?) -> Unit) {

        val url: String = baseURL + "accounts?address=$address"

        Fuel.get(url).responseObject(AccountResponse.Deserializer()) { _, _, result ->
            val (response, err) = result
            callback(response?.account, err)
        }
    }

    /**
     * Public accessor method for fetching the balance of an Ark Address
     *
     * Example: balance": "6195626142833"
     * @param address ip address of the Ark node
     * @param callback The callback returned after the network request. Contains an optional Ark balance Double` and `FuelErrror`
     *
     */
    public fun getBalance(address: String, callback: (account: Double?, error: FuelError?) -> Unit) {

        val url: String = baseURL + "accounts?address=$address"

        Fuel.get(url).responseObject(AccountResponse.Deserializer()) { _, _, result ->
            val (response, err) = result
            callback(response?.account?.arkBalance, err)
        }
    }

    /**
     * Public accessor method for fetching the voted delegate for the specified Ark Account Address
     *
     * Will be nil if Ark Account hasn't voted for a delegate
     *
     * Example Ark Delegate Vote:
     * {"username": "goose",
     * "address": "ALLZ3TQKTaHm2Bte4SrXL9C5cS8ZovqFfZ",
     * "publicKey": "03c5d32dedf5441b3aafb2e0c6ad3e5568bb0b3e822807b133e2276e014d830e3c",
     * "vote": "153329696843480",
     * "producedblocks": 11911,
     * "missedblocks": 79,
     * "rate": 10,
     * "approval": 1.18,
     * "productivity": 99.34}
     *
     * @param address ip address of the Ark node
     * @param callback The callback returned after the network request. Contains an optional Ark `Delegate` and `FuelErrror`
     *
     */
    public fun getVote(address: String, callback: (delegate: Delegate?, error: FuelError?) -> Unit) {

        val url: String = baseURL + "accounts/delegates?address=$address"

        Fuel.get(url).responseObject(DelegatesResponse.Deserializer()) { _, _, result ->
            val (response, err) = result
            callback(response?.delegates?.first(), err)
        }
    }

    /**
     * Public accessor method for fetching the public key of an Ark Address
     *
     * Example: "publicKey": "03c5d32dedf5441b3aafb2e0c6ad3e5568bb0b3e822807b133e2276e014d830e3c"
     * @param address ip address of the Ark node
     * @param callback The callback returned after the network request. Contains an optional Ark publicKey `String` and `FuelErrror`
     *
     */
    public fun getPublicKey(address: String, callback: (account: String?, error: FuelError?) -> Unit) {

        val url: String = baseURL + "accounts?address=$address"

        Fuel.get(url).responseObject(AccountResponse.Deserializer()) { _, _, result ->
            val (response, err) = result
            callback(response?.account?.publicKey, err)
        }
    }

    // Blocks

    /**
     * Public accessor method for fetching the a block from the specified `Block Id`
     *
     * Example Block:
     * "id": "16702014946798127280",
     * "version": 0,
     * "timestamp": 20786608,
     * "height": 2571606,
     * "previousBlock": "7503383488254058908",
     * "numberOfTransactions": 0,
     * "totalAmount": 0,
     * "totalFee": 0,
     * "reward": 200000000,
     * "payloadLength": 0,
     * "payloadHash": "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855",
     * "generatorPublicKey": "020431436cf94f3c6a6ba566fe9e42678db8486590c732ca6c3803a10a86f50b92",
     * generatorId": "AKdr5d9AMEnsKYxpDcoHdyyjSCKVx3r9Nj",
     * "blockSignature": "304502210083d71796a0990e15092fa6a56b8b756e8baeed92506255518dff640d723199e702203ed99a4db2f3da1658b1a583d77d638113c6453e1ddb3c2b67347e171aa78fb6",
     * "confirmations": 5,
     * "totalForged": "200000000"
     *
     * @param blockID Id for an Ark Block
     * @param callback The callback returned after the network request. Contains an optional Ark `Block` and `FuelErrror`
     *
     */
    public fun getBlock(blockID: String, callback: (block: Block?, error: FuelError?) -> Unit) {

        val url: String = baseURL + "blocks/get?id=$blockID"

        Fuel.get(url).responseObject(BlockResponse.Deserializer()) { _, _, result ->
            val (response, err) = result
            callback(response?.block, err)
        }
    }

    /**
     * Public accessor method for fetching the most recent blocks on the current node
     *
     * Example Block:
     * "id": "16702014946798127280",
     * "version": 0,
     * "timestamp": 20786608,
     * "height": 2571606,
     * "previousBlock": "7503383488254058908",
     * "numberOfTransactions": 0,
     * "totalAmount": 0,
     * "totalFee": 0,
     * "reward": 200000000,
     * "payloadLength": 0,
     * "payloadHash": "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855",
     * "generatorPublicKey": "020431436cf94f3c6a6ba566fe9e42678db8486590c732ca6c3803a10a86f50b92",
     * generatorId": "AKdr5d9AMEnsKYxpDcoHdyyjSCKVx3r9Nj",
     * "blockSignature": "304502210083d71796a0990e15092fa6a56b8b756e8baeed92506255518dff640d723199e702203ed99a4db2f3da1658b1a583d77d638113c6453e1ddb3c2b67347e171aa78fb6",
     * "confirmations": 5,
     * "totalForged": "200000000"
     *
     * @param callback The callback returned after the network request. Contains an optional array of Ark `Block` and `FuelErrror`
     *
     */
    public fun getBlocks(callback: (blocks: Array<Block>?, error: FuelError?) -> Unit) {

        val url: String = baseURL + "blocks"

        Fuel.get(url).responseObject(BlocksResponse.Deserializer()) { _, _, result ->
            val (response, err) = result
            callback(response?.blocks, err)
        }
    }

    /**
     * Public accessor method for fetching blocks forged by the specified delegate
     *
     * Example Block:
     * "id": "16702014946798127280",
     * "version": 0,
     * "timestamp": 20786608,
     * "height": 2571606,
     * "previousBlock": "7503383488254058908",
     * "numberOfTransactions": 0,
     * "totalAmount": 0,
     * "totalFee": 0,
     * "reward": 200000000,
     * "payloadLength": 0,
     * "payloadHash": "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855",
     * "generatorPublicKey": "020431436cf94f3c6a6ba566fe9e42678db8486590c732ca6c3803a10a86f50b92",
     * generatorId": "AKdr5d9AMEnsKYxpDcoHdyyjSCKVx3r9Nj",
     * "blockSignature": "304502210083d71796a0990e15092fa6a56b8b756e8baeed92506255518dff640d723199e702203ed99a4db2f3da1658b1a583d77d638113c6453e1ddb3c2b67347e171aa78fb6",
     * "confirmations": 5,
     * "totalForged": "200000000"
     *
     * @param delegate Ark Delegate
     * @param limit Number of blocks returned. Maximum 51
     * @param offset Query paging offset
     * @param callback The callback returned after the network request. Contains an optional array of Ark `Block` and `FuelErrror`
     *
     */
    public fun getBlocks(delegate: Delegate, limit: Int, offset: Int, callback: (blocks: Array<Block>?, error: FuelError?) -> Unit) {

        val url: String = baseURL + "blocks?generatorPublicKey=${delegate.publicKey}&limit=$limit&offset=$offset&orderBy=height:desc"

        Fuel.get(url).responseObject(BlocksResponse.Deserializer()) { _, _, result ->
            val (response, err) = result
            callback(response?.blocks, err)
        }
    }

    /**
     * Public accessor method for fetching forged Ark blocks
     *
     * Example Block:
     * "id": "16702014946798127280",
     * "version": 0,
     * "timestamp": 20786608,
     * "height": 2571606,
     * "previousBlock": "7503383488254058908",
     * "numberOfTransactions": 0,
     * "totalAmount": 0,
     * "totalFee": 0,
     * "reward": 200000000,
     * "payloadLength": 0,
     * "payloadHash": "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855",
     * "generatorPublicKey": "020431436cf94f3c6a6ba566fe9e42678db8486590c732ca6c3803a10a86f50b92",
     * generatorId": "AKdr5d9AMEnsKYxpDcoHdyyjSCKVx3r9Nj",
     * "blockSignature": "304502210083d71796a0990e15092fa6a56b8b756e8baeed92506255518dff640d723199e702203ed99a4db2f3da1658b1a583d77d638113c6453e1ddb3c2b67347e171aa78fb6",
     * "confirmations": 5,
     * "totalForged": "200000000"
     *
     * @param limit Number of blocks returned. Maximum 51
     * @param offset Query paging offset
     * @param callback The callback returned after the network request. Contains an optional array of Ark `Block` and `FuelErrror`
     *
     */
    public fun getBlocks(publicKey: String, limit: Int, offset: Int, callback: (blocks: Array<Block>?, error: FuelError?) -> Unit) {

        val url: String = baseURL + "blocks?limit=$limit&offset=$offset&orderBy=height:desc"

        Fuel.get(url).responseObject(BlocksResponse.Deserializer()) { _, _, result ->
            val (response, err) = result
            callback(response?.blocks, err)
        }
    }

    /**
     * Public accessor method for fetching the last forged block
     *
     * Example Block:
     * "id": "16702014946798127280",
     * "version": 0,
     * "timestamp": 20786608,
     * "height": 2571606,
     * "previousBlock": "7503383488254058908",
     * "numberOfTransactions": 0,
     * "totalAmount": 0,
     * "totalFee": 0,
     * "reward": 200000000,
     * "payloadLength": 0,
     * "payloadHash": "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855",
     * "generatorPublicKey": "020431436cf94f3c6a6ba566fe9e42678db8486590c732ca6c3803a10a86f50b92",
     * generatorId": "AKdr5d9AMEnsKYxpDcoHdyyjSCKVx3r9Nj",
     * "blockSignature": "304502210083d71796a0990e15092fa6a56b8b756e8baeed92506255518dff640d723199e702203ed99a4db2f3da1658b1a583d77d638113c6453e1ddb3c2b67347e171aa78fb6",
     * "confirmations": 5,
     * "totalForged": "200000000"
     *
     * @param callback The callback returned after the network request. Contains an optional Ark `Block` and `FuelErrror`
     *
     */
    public fun getLastBlock(callback: (block: Block?, error: FuelError?) -> Unit) {

        val url: String = baseURL + "blocks?limit=1&offset=0&orderBy=height:desc"

        Fuel.get(url).responseObject(BlocksResponse.Deserializer()) { _, _, result ->
            val (response, err) = result
            callback(response?.blocks?.first(), err)
        }
    }

    // Delegates


    /**
     * Public accessor method for fetching an Ark Delegate
     *
     * Example Ark Delegate:
     * "delegate": {"username": "goose",
     * "address": "ALLZ3TQKTaHm2Bte4SrXL9C5cS8ZovqFfZ",
     * "publicKey": "03c5d32dedf5441b3aafb2e0c6ad3e5568bb0b3e822807b133e2276e014d830e3c",
     * "vote": "153330296843480",
     * "producedblocks": 11914,
     * "missedblocks": 79,
     * "rate": 10,
     * "approval": 1.18,
     * "productivity": 99.34}
     *
     * @param delegate Ark Delegate username
     * @param callback The callback returned after the network request. Contains an optional Ark `Delegate` and `FuelErrror`
     *
     */
    public fun getDelegate(delegate: String, callback: (delegate: Delegate?, error: FuelError?) -> Unit) {

        val url: String = baseURL + "delegates/get?username=$delegate"

        Fuel.get(url).responseObject(DelegateResponse.Deserializer()) { _, _, result ->
            val (response, err) = result
            callback(response?.delegate, err)
        }
    }

    /**
     * Public accessor method for fetching the list of forging Ark Delegates
     *
     * Example Ark Delegate:
     * "delegate": {"username": "goose",
     * "address": "ALLZ3TQKTaHm2Bte4SrXL9C5cS8ZovqFfZ",
     * "publicKey": "03c5d32dedf5441b3aafb2e0c6ad3e5568bb0b3e822807b133e2276e014d830e3c",
     * "vote": "153330296843480",
     * "producedblocks": 11914,
     * "missedblocks": 79,
     * "rate": 10,
     * "approval": 1.18,
     * "productivity": 99.34}
     *
     * @param callback The callback returned after the network request. Contains an optional array of Ark `Delegate` and `FuelErrror`
     *
     */
    public fun getDelegates(callback: (delegate: Array<Delegate>?, error: FuelError?) -> Unit) {

        val url: String = baseURL + "delegates"


        Fuel.get(url).responseObject(DelegatesResponse.Deserializer()) { _, _, result ->
            val (response, err) = result
            callback(response?.delegates, err)
        }
    }

    /**
     * Public accessor method for fetching the list of Ark standby Delegates (position 52-102)
     *
     * Example Ark Delegate:
     * "delegate": {"username": "goose",
     * "address": "ALLZ3TQKTaHm2Bte4SrXL9C5cS8ZovqFfZ",
     * "publicKey": "03c5d32dedf5441b3aafb2e0c6ad3e5568bb0b3e822807b133e2276e014d830e3c",
     * "vote": "153330296843480",
     * "producedblocks": 11914,
     * "missedblocks": 79,
     * "rate": 10,
     * "approval": 1.18,
     * "productivity": 99.34}
     *
     * @param callback The callback returned after the network request. Contains an optional array of Ark `Delegate` and `FuelErrror`
     *
     */
    public fun getStandbyDelegates(callback: (delegate: Array<Delegate>?, error: FuelError?) -> Unit) {

        val url: String = baseURL + "delegates?limit=51&offset=51&orderBy=rate:asc"

        Fuel.get(url).responseObject(DelegatesResponse.Deserializer()) { _, _, result ->
            val (response, err) = result
            callback(response?.delegates, err)
        }
    }

    /**
     * Public accessor method for fetching Ark Delegates with specified limit and offset
     *
     * Example Ark Delegate:
     * "delegate": {"username": "goose",
     * "address": "ALLZ3TQKTaHm2Bte4SrXL9C5cS8ZovqFfZ",
     * "publicKey": "03c5d32dedf5441b3aafb2e0c6ad3e5568bb0b3e822807b133e2276e014d830e3c",
     * "vote": "153330296843480",
     * "producedblocks": 11914,
     * "missedblocks": 79,
     * "rate": 10,
     * "approval": 1.18,
     * "productivity": 99.34}
     *
     * @param limit Number of blocks returned. Maximum 50
     * @param offset Query paging offset
     * @param callback The callback returned after the network request. Contains an optional array of Ark `Delegate` and `FuelErrror`
     *
     */
    public fun getDelegates(limit: Int, offset: Int, callback: (delegate: Array<Delegate>?, error: FuelError?) -> Unit) {

        val url: String = baseURL + "delegates?limit=$limit&offset=$offset&orderBy=rate:asc"

        Fuel.get(url).responseObject(DelegatesResponse.Deserializer()) { _, _, result ->
            val (response, err) = result
            callback(response?.delegates, err)
        }
    }

    /**
     * Public accessor method for fetching the current voters of the specified Ark Delegate
     *
     * Example Ark Voter:
     * {"username": "hami",
     * "address": "AZAwgx7NpEiow217jxFEKA85yYnuiHzsj7",
     * "publicKey": "02812aeaa258090daca583386107d0a3c09c13b2df4163510a29fca8a7798fa0ec",
     * "balance": "22584166666735"}
     *
     * @param delegate Ark Delegate
     * @param callback The callback returned after the network request. Contains an optional array of Ark `Voter` and `FuelErrror`
     *
     */
    public fun getVoters(delegate: Delegate, callback: (voters: Array<Voter>?, error: FuelError?) -> Unit) {

        val url: String = baseURL + "delegates/voters?publicKey=${delegate.publicKey}"

        Fuel.get(url).responseObject(VotersResponse.Deserializer()) { _, _, result ->
            val (response, err) = result
            callback(response?.accounts, err)
        }
    }

    /**
     * Public accessor method for fetching the current voters of the specified Ark Delegate publicKey
     *
     * Example Ark Voter:
     * {"username": "hami",
     * "address": "AZAwgx7NpEiow217jxFEKA85yYnuiHzsj7",
     * "publicKey": "02812aeaa258090daca583386107d0a3c09c13b2df4163510a29fca8a7798fa0ec",
     * "balance": "22584166666735"}
     *
     * @param publicKey Ark Delegate publicKey
     * @param callback The callback returned after the network request. Contains an optional array of Ark `Voter` and `FuelErrror`
     *
     */
    public fun getVoters(publicKey: String, callback: (voters: Array<Voter>?, error: FuelError?) -> Unit) {

        val url: String = baseURL + "delegates/voters?publicKey=$publicKey"

        Fuel.get(url).responseObject(VotersResponse.Deserializer()) { _, _, result ->
            val (response, err) = result
            callback(response?.accounts, err)
        }
    }

    // Peers

    /**
     * Public accessor method for fetching current Ark Peers
     *
     * Example Ark Peer:
     * {"ip": "94.176.236.198",
     * "port": 4001,
     * "version": "1.0.2",
     * "errors": 0,
     * "os": "linux4.4.0-98-generic",
     * "height": 2571819,
     * "status": "OK",
     * "delay": 247}
     *
     * @param callback The callback returned after the network request. Contains an optional array of Ark `Peer` and `FuelErrror`
     *
     */
    public fun getPeers(callback: (peers: Array<Peer>?, error: FuelError?) -> Unit) {

        val url: String = baseURL + "peers"

        Fuel.get(url).responseObject(PeersResponse.Deserializer()) { _, _, result ->
            val (response, err) = result
            callback(response?.peers, err)
        }
    }


    /**
     * Public accessor method for fetching a specific Ark Peer
     *
     * Example Ark Peer:
     * {"ip": "94.176.236.198",
     * "port": 4001,
     * "version": "1.0.2",
     * "errors": 0,
     * "os": "linux4.4.0-98-generic",
     * "height": 2571819,
     * "status": "OK",
     * "delay": 247}
     *
     * @param ip The Ark Peer IP address
     * @param port The Ark Peer port. Default for production is `4001`
     * @param callback The callback returned after the network request. Contains an optional Ark `Peer` and `FuelErrror`
     *
     */
    public fun getPeer(ip: String, port: Int, callback: (peer: Peer?, error: FuelError?) -> Unit) {

        val url: String = baseURL + "peers/get?ip=$ip&port=$port"

        Fuel.get(url).responseObject(PeerResponse.Deserializer()) { _, _, result ->
            val (response, err) = result
            callback(response?.peer, err)
        }
    }

    /**
     * Public accessor method for fetching a specific Ark Peer
     *
     * Example Ark PeerVersion:
     * {"success": true,
     * "version": "1.0.1",
     * "build": ""}
     *
     * @param callback The callback returned after the network request. Contains an optional Ark `PeerVersion` and `FuelErrror`
     *
     */
    public fun getPeerVersion(callback: (version: PeerVersion?, error: FuelError?) -> Unit) {

        val url: String = baseURL + "peers/version"

        Fuel.get(url).responseObject(PeerVersion.Deserializer()) { _, _, result ->
            val (response, err) = result
            callback(response, err)
        }
    }

    // Status

    /**
     * Public accessor method for fetching a current Ark Sync Status
     *
     * Example Ark Ark Sync Status:
     * {"success": true,
     * "epoch": "2017-03-21T13:00:00.000Z",
     * "height": 2571922,
     * "fee": 10000000,
     * "milestone": 0,
     * "nethash": "6e84d08bd299ed97c212c886c98a57e36545c8f5d645ca7eeae63a8bd62d8988",
     * "reward": 200000000,
     * "supply": 13014384400000000}
     *
     * @param callback The callback returned after the network request. Contains an optional Ark `Status` and `FuelErrror`
     *
     */
    public fun getSyncStatus(callback: (status: Status?, error: FuelError?) -> Unit) {
        val url: String = baseURL + "loader/status/sync"

        Fuel.get(url).responseObject(Status.Deserializer()) { _, _, result ->
            val (response, err) = result
            callback(response, err)
        }
    }

    // Transactions

    /**
     * Public accessor method for fetching the list of most recent Ark transactions
     *
     * Example Ark Transaction:
     * {"id": "09689fe52251a80429aa36a5293f9e963bff460c5dc409a3b6be777a73cf4762",
     * "blockid": "14730629575185672828",
     * "type": 3,
     * "timestamp": 15529836,
     * "amount": 0,
     * "fee": 100000000,
     * "senderId": "AMf2pWEXzQE1JgZLZ98dK9aV73xebQUtV4",
     * "recipientId": "AMf2pWEXzQE1JgZLZ98dK9aV73xebQUtV4",
     * "senderPublicKey": "02405e33c6ebb096abfb3a772eed3ab2049aea3e036bace6c1d4974258d94773ea",
     * "signature": "304402200708bb2538fdffd552cec07ee7ae90fbe0a66a0be0b6cdd849d1af17ae4df4cf022056f53d465df5ebfd9019bc7231219487989a0cfc8556f4d433d53fffccae5d7d",
     * "asset": {"votes": ["+0218b77efb312810c9a549e2cc658330fcc07f554d465673e08fa304fa59e67a0a"]},
     * "confirmations": 641728}
     *
     * @param callback The callback returned after the network request. Contains an optional array of Ark `Transactions` and `FuelErrror`
     *
     */
    public fun getTransactions(callback: (transactions: Array<ArkTransaction>?, error: FuelError?) -> Unit) {

        val url: String = baseURL + "transactions"

        Fuel.get(url).responseObject(TransactionsResponse.Deserializer()) { _, _, result ->
            val (response, err) = result
            callback(response?.transactions, err)
        }
    }

    /**
     * Public accessor method for fetching the list of Ark transactions
     *
     * Example Ark Transaction:
     * {"id": "09689fe52251a80429aa36a5293f9e963bff460c5dc409a3b6be777a73cf4762",
     * "blockid": "14730629575185672828",
     * "type": 3,
     * "timestamp": 15529836,
     * "amount": 0,
     * "fee": 100000000,
     * "senderId": "AMf2pWEXzQE1JgZLZ98dK9aV73xebQUtV4",
     * "recipientId": "AMf2pWEXzQE1JgZLZ98dK9aV73xebQUtV4",
     * "senderPublicKey": "02405e33c6ebb096abfb3a772eed3ab2049aea3e036bace6c1d4974258d94773ea",
     * "signature": "304402200708bb2538fdffd552cec07ee7ae90fbe0a66a0be0b6cdd849d1af17ae4df4cf022056f53d465df5ebfd9019bc7231219487989a0cfc8556f4d433d53fffccae5d7d",
     * "asset": {"votes": ["+0218b77efb312810c9a549e2cc658330fcc07f554d465673e08fa304fa59e67a0a"]},
     * "confirmations": 641728}
     *
     * @param limit Number of blocks returned. Maximum 50
     * @param offset Query paging offset
     * @param callback The callback returned after the network request. Contains an optional array of Ark `Transactions` and `FuelErrror`
     *
     */
    public fun getTransactions(limit: Int, offset: Int, callback: (transactions: Array<ArkTransaction>?, error: FuelError?) -> Unit) {

        val url: String = baseURL + "transactions?limit=$limit&offset=$offset"

        Fuel.get(url).responseObject(TransactionsResponse.Deserializer()) { _, _, result ->
            val (response, err) = result
            callback(response?.transactions, err)
        }
    }

    /**
     * Public accessor method for fetching an Ark transaction
     *
     * Example Ark Transaction:
     * {"id": "09689fe52251a80429aa36a5293f9e963bff460c5dc409a3b6be777a73cf4762",
     * "blockid": "14730629575185672828",
     * "type": 3,
     * "timestamp": 15529836,
     * "amount": 0,
     * "fee": 100000000,
     * "senderId": "AMf2pWEXzQE1JgZLZ98dK9aV73xebQUtV4",
     * "recipientId": "AMf2pWEXzQE1JgZLZ98dK9aV73xebQUtV4",
     * "senderPublicKey": "02405e33c6ebb096abfb3a772eed3ab2049aea3e036bace6c1d4974258d94773ea",
     * "signature": "304402200708bb2538fdffd552cec07ee7ae90fbe0a66a0be0b6cdd849d1af17ae4df4cf022056f53d465df5ebfd9019bc7231219487989a0cfc8556f4d433d53fffccae5d7d",
     * "asset": {"votes": ["+0218b77efb312810c9a549e2cc658330fcc07f554d465673e08fa304fa59e67a0a"]},
     * "confirmations": 641728}
     *
     * @param transactionID Ark transaction ID
     * @param callback The callback returned after the network request. Contains an optional Ark `Transactions` and `FuelErrror`
     *
     */
    public fun getTransaction(transactionID: String, callback: (transaction: ArkTransaction?, error: FuelError?) -> Unit) {

        val url: String = baseURL + "transactions/get?id=$transactionID"

        Fuel.get(url).responseObject(TransactionResponse.Deserializer()) { _, _, result ->
            val (response, err) = result
            callback(response?.transaction, err)
        }
    }

    /**
     * Public accessor method for fetching the list of most recent Ark transactions sent by a particular Ark Address
     *
     * Example Ark Transaction:
     * {"id": "09689fe52251a80429aa36a5293f9e963bff460c5dc409a3b6be777a73cf4762",
     * "blockid": "14730629575185672828",
     * "type": 3,
     * "timestamp": 15529836,
     * "amount": 0,
     * "fee": 100000000,
     * "senderId": "AMf2pWEXzQE1JgZLZ98dK9aV73xebQUtV4",
     * "recipientId": "AMf2pWEXzQE1JgZLZ98dK9aV73xebQUtV4",
     * "senderPublicKey": "02405e33c6ebb096abfb3a772eed3ab2049aea3e036bace6c1d4974258d94773ea",
     * "signature": "304402200708bb2538fdffd552cec07ee7ae90fbe0a66a0be0b6cdd849d1af17ae4df4cf022056f53d465df5ebfd9019bc7231219487989a0cfc8556f4d433d53fffccae5d7d",
     * "asset": {"votes": ["+0218b77efb312810c9a549e2cc658330fcc07f554d465673e08fa304fa59e67a0a"]},
     * "confirmations": 641728}
     *
     *  @param address Ark Address
     * @param callback The callback returned after the network request. Contains an optional array of Ark `Transactions` and `FuelErrror`
     *
     */
    public fun getSentTransactions(address: String, callback: (transactions: Array<ArkTransaction>?, error: FuelError?) -> Unit) {

        val url: String = baseURL + "transactions?senderId=$address"


        Fuel.get(url).responseObject(TransactionsResponse.Deserializer()) { _, _, result ->
            val (response, err) = result
            callback(response?.transactions, err)
        }
    }

    /**
     * Public accessor method for fetching the list of most recent Ark transactions received by a particular Ark Address
     *
     * Example Ark Transaction:
     * {"id": "09689fe52251a80429aa36a5293f9e963bff460c5dc409a3b6be777a73cf4762",
     * "blockid": "14730629575185672828",
     * "type": 3,
     * "timestamp": 15529836,
     * "amount": 0,
     * "fee": 100000000,
     * "senderId": "AMf2pWEXzQE1JgZLZ98dK9aV73xebQUtV4",
     * "recipientId": "AMf2pWEXzQE1JgZLZ98dK9aV73xebQUtV4",
     * "senderPublicKey": "02405e33c6ebb096abfb3a772eed3ab2049aea3e036bace6c1d4974258d94773ea",
     * "signature": "304402200708bb2538fdffd552cec07ee7ae90fbe0a66a0be0b6cdd849d1af17ae4df4cf022056f53d465df5ebfd9019bc7231219487989a0cfc8556f4d433d53fffccae5d7d",
     * "asset": {"votes": ["+0218b77efb312810c9a549e2cc658330fcc07f554d465673e08fa304fa59e67a0a"]},
     * "confirmations": 641728}
     *
     * @param address Ark Address
     * @param callback The callback returned after the network request. Contains an optional array of Ark `Transactions` and `FuelErrror`
     *
     */
    public fun getReceivedTransactions(address: String, callback: (transactions: Array<ArkTransaction>?, error: FuelError?) -> Unit) {

        val url: String = baseURL + "transactions?recipientId=$address"

        Fuel.get(url).responseObject(TransactionsResponse.Deserializer()) { _, _, result ->
            val (response, err) = result
            callback(response?.transactions, err)
        }
    }

    /**
     * Public method to send a Transaction to Ark Address
     *
     * @param recipientID Recipient Ark Address
     * @param amount Transaction amount
     * @param passphrase Ark Address secret passphrase
     * @param secondPassphrase Optional second secret passphrase
     * @param vendorField Optional transaction vendorfield
     *
     */
    public fun sendTransaction(recipientID: String, amount: Long, passphrase: String, secondPassphrase: String?, vendorField: String?) {

        val mainnet = io.ark.core.Network.getMainnet()

        mainnet.warmup()

        val transaction = io.ark.core.Transaction.createTransaction(recipientID, amount, vendorField, passphrase, secondPassphrase)

        mainnet.randomPeer.postTransaction(transaction)
    }

    /**
     * Public method to vote for a current `Ark Delegate`
     *
     * @param delegate Recipient Ark Address
     * @param passphrase Ark Address secret passphrase
     * @param secondPassphrase Optional second secret passphrase
     *
     */
    public fun sendVote(delegate: Delegate, passphrase: String, secondPassphrase: String?) {
        val mainnet = io.ark.core.Network.getMainnet()

        mainnet.warmup()

        val vote = "+" + delegate.publicKey

        val votes = arrayListOf<String>(vote)

        val transaction = io.ark.core.Transaction.createVote(votes, passphrase, secondPassphrase)

        mainnet.randomPeer.postTransaction(transaction)
    }

    /**
     * Public method to unvote for a current `Ark Delegate`
     *
     * @param delegate Recipient Ark Address
     * @param passphrase Ark Address secret passphrase
     * @param secondPassphrase Optional second secret passphrase
     *
     */
    public fun sendUnvote(delegate: Delegate, passphrase: String, secondPassphrase: String?) {
        val mainnet = io.ark.core.Network.getMainnet()

        mainnet.warmup()

        val vote = "-" + delegate.publicKey

        val votes = arrayListOf<String>(vote)

        val transaction = io.ark.core.Transaction.createVote(votes, passphrase, secondPassphrase)

        mainnet.randomPeer.postTransaction(transaction)
    }

    // Ticker

    /**
     * Public accessor method for fetching the current Ark Ticker
     *
     * Example Ark Ticker:
     * {"AUD": 4.14,
     * "BRL": 10.16,
     * "BTC": 0.0003848,
     * "CAD": 3.94,
     * "CLP": 1980.42,
     * "CNY": 20.66,
     * "CZK": 66.45,
     * "DKK": 20.36,
     * "EUR": 2.59,
     * "GBP": 2.32,
     * "HKD": 24.61,
     * "IDR": 41272.16,
     * "INR": 199.94,
     * "JPY": 355.25,
     * "KRW": 3368.15,
     * "MXN": 57.6,
     * "NOK": 25.69,
     * "NZD": 4.46,
     * "PHP": 155.85,
     * "PKR": 350.55,
     * "RUB": 167.2,
     * "SEK": 25.05,
     * "THB": 99.28,
     * "TWD": 88.45,
     * "USD": 3.05}
     *
     * @param callback The callback returned after the network request. Contains an optional Ark `Ticker` and `FuelErrror`
     *
     */
    public fun getTicker(callback: (ticker: Ticker?, error: FuelError?) -> Unit) {

        val url: String = "https://min-api.cryptocompare.com/data/price?fsym=ARK&tsyms=AUD,BRL,BTC,CAD,CLP,CNY,CZK,DKK,EUR,GBP,HKD,IDR,INR,JPY,KRW,MXN,NOK,NZD,PHP,PKR,RUB,SEK,THB,TWD,USD"

        Fuel.get(url).responseObject(Ticker.Deserializer()) { _, _, result ->
            val (response, err) = result
            callback(response, err)
        }
    }

    private fun setNetworkPreferences() {
        FuelManager.instance.basePath = baseURL
    }
}