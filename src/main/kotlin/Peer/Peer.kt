package io.arkk.arkk

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson


/**
 *
 * Ark Peer Response class to handle data from server
 *
 */
public data class PeerResponse(
        val success: Boolean,
        val peer: Peer?
)  {

    class Deserializer : ResponseDeserializable<PeerResponse> {
        override fun deserialize(content: String) = Gson().fromJson(content, PeerResponse::class.java)
    }
}

/**
 *
 * Ark Peers Response class to handle data from server
 *
 */
public data class PeersResponse(
        val success: Boolean,
        val peers: Array<Peer>?
)  {

    class Deserializer : ResponseDeserializable<PeersResponse> {
        override fun deserialize(content: String) = Gson().fromJson(content, PeersResponse::class.java)
    }
}


/**
 *
 * Ark Peer Data class
 *
 */
public data class Peer(

        /**
         *
         * Peer ip address
         *
         */
        public val ip: String,

        /**
         *
         * Peer port number
         *
         */
        public val port: Int,

        /**
         *
         * Peer server version
         *
         */
        public val version: String,

        /**
         *
         * Number of errrors
         *
         */
        public val errors: Int,

        /**
         *
         * Peer operating system
         *
         */
        public val os : String,

        /**
         *
         * Peer height
         *
         */
        public val height: Int,

        /**
         *
         * Peer status
         *
         */
        public val status: String,

        /**
         *
         * Peer delay
         *
         */
        public var delay: Int
)
{

    class Deserializer : ResponseDeserializable<Peer> {
        override fun deserialize(content: String) = Gson().fromJson(content, Peer::class.java)
    }
}

/**
 *
 * Ark PeerVersion Data class
 *
 */
public data class PeerVersion(

        /**
         *
         * Peer version
         *
         */
        public val version: String,

        /**
         *
         * Peer builc
         *
         */
        public val build: String
)
{

    class Deserializer : ResponseDeserializable<PeerVersion> {
        override fun deserialize(content: String) = Gson().fromJson(content, PeerVersion::class.java)
    }
}



