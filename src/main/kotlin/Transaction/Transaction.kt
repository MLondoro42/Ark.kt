package io.arkk.arkk

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson


/**
 *
 * Ark Transaction Response class to handle data from server
 *
 */
public data class TransactionResponse(
        val success: Boolean,
        val transaction: ArkTransaction?
)  {

    class Deserializer : ResponseDeserializable<TransactionResponse> {
        override fun deserialize(content: String) = Gson().fromJson(content, TransactionResponse::class.java)
    }
}


/**
 *
 * Ark Transactions Response class to handle data from server
 *
 */
public data class TransactionsResponse(
        val success: Boolean,
        val transactions: Array<ArkTransaction>?
)  {

    class Deserializer : ResponseDeserializable<TransactionsResponse> {
        override fun deserialize(content: String) = Gson().fromJson(content, TransactionsResponse::class.java)
    }
}

/**
 *
 * Ark ArkTransaction Data class
 *
 */
public data class ArkTransaction(

        /**
         *
         * Ark transaction ID
         *
         */
        public val id: String,

        /**
         *
         * Ark transaction block ID
         *
         */
        public val blockid: String,

        /**
         *
         * Ark transaction height
         *
         */
        public val height: Int,

        /**
         *
         * Ark transaction type
         *
         */
        public val type: Int,

        /**
         *
         * Ark transaction amount
         *
         */
        public val amount: Double,

        /**
         *
         * Ark transaction fee
         *
         */
        public val fee: Double,

        /**
         *
         * Ark transaction sender ID
         *
         */
        public val senderId: String,

        /**
         *
         * Ark transaction recipient ID
         *
         */
        public val recipientId: String,

        /**
         *
         * Ark transaction sender public key
         *
         */
        public val senderPublicKey: String,

        /**
         *
         * Ark transaction signature
         *
         */
        public val signature: String,

        /**
         *
         * Ark transaction confirmation count
         *
         */
        public val confirmations: Int
)  {

    class Deserializer : ResponseDeserializable<ArkTransaction> {
        override fun deserialize(content: String) = Gson().fromJson(content, ArkTransaction::class.java)
    }
}


