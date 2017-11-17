package io.arkk.arkk

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

/**
 *
 * Ark Block Response class to handle data from server
 *
 */
public data class BlockResponse(
        val success: Boolean,
        val block: Block?
)  {

    class Deserializer : ResponseDeserializable<BlockResponse> {
        override fun deserialize(content: String) = Gson().fromJson(content, BlockResponse::class.java)
    }
}

/**
 *
 * Ark Blocks Response class to handle data from server
 *
 */
public data class BlocksResponse(
        val success: Boolean,
        val blocks: Array<Block>?
)  {

    class Deserializer : ResponseDeserializable<BlocksResponse> {
        override fun deserialize(content: String) = Gson().fromJson(content, BlocksResponse::class.java)
    }
}

/**
 *
 * Ark Block Data class
 *
 */
public data class Block(

        /**
         *
         * Ark Block id
         *
         */
        public val id: String,

        /**
         *
         * Ark Block version
         *
         */
        public val version: Int,

        /**
         *
         * Ark Block timestamp
         *
         */
        public val timestamp: Int,

        /**
         *
         * Ark Block height
         *
         */
        public val height: Int,

        /**
         *
         * Previous Ark Block
         *
         */
        public val previousBlock : String,

        /**
         *
         * Number of transactions
         *
         */
        public val numberOfTransactions : Int,

        /**
         *
         * Total block amount
         *
         */
        public val totalAmount : Double,

        /**
         *
         * Total block fee
         *
         */
        public val totalFee : Double,

        /**
         *
         * Total block reward
         *
         */
        public val reward : Double,

        /**
         *
         * Ark block payload length
         *
         */
        public val payloadLength : Int,

        /**
         *
         * Ark block payload hash
         *
         */
        public val payloadHash : String,

        /**
         *
         * Ark block generator public key
         *
         */
        public val generatorPublicKey : String,

        /**
         *
         * Ark block generator id
         *
         */
        public val generatorId : String,

        /**
         *
         * Ark block signature
         *
         */
        public val blockSignature: String,

        /**
         *
         * Ark block confirmations
         *
         */
        public val confirmations: Int,

        /**
         *
         * Total forged
         *
         */
        public val totalForged: Double
)  {

    class Deserializer : ResponseDeserializable<Block> {
        override fun deserialize(content: String) = Gson().fromJson(content, Block::class.java)
    }
}




