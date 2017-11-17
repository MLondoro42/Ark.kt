package io.arkk.arkk

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

/**
 *
 * Ark Delegate Response class to handle data from server
 *
 */
public data class DelegateResponse(
        val success: Boolean,
        val delegate: Delegate?
)  {

    class Deserializer : ResponseDeserializable<DelegateResponse> {
        override fun deserialize(content: String) = Gson().fromJson(content, DelegateResponse::class.java)
    }
}


/**
 *
 * Ark Delegates Response class to handle data from server
 *
 */
public data class DelegatesResponse(
        val success: Boolean,
        val delegates: Array<Delegate>?
)  {

    class Deserializer : ResponseDeserializable<DelegatesResponse> {
        override fun deserialize(content: String) = Gson().fromJson(content, DelegatesResponse::class.java)
    }
}

/**
 *
 * Ark Delegate Data class
 *
 */
public data class Delegate(

        /**
         *
         * Ark delegate username
         *
         */
        val username: String,

        /**
         *
         * Ark delegate address
         *
         */
        val address: String,

        /**
         *
         * Ark delegate publicKey
         *
         */
        val publicKey: String,

        /**
         *
         * Number of produced blocks
         *
         */
        val producedblocks: Int,

        /**
         *
         * Number of missed blocks
         *
         */
        var missedBlocks: Int,

        /**
         *
         * Current rate/ranking
         *
         */
        val rate: Int,

        /**
         *
         * Current approval percentage
         *
         */
        val approval: Float,

        /**
         *
         * Current productivity percentage
         *
         */
        val productivity: Float,

        /**
         *
         * Ark Delegate vote
         *
         */
        val vote: String
)  {

    class Deserializer : ResponseDeserializable<Delegate> {
        override fun deserialize(content: String) = Gson().fromJson(content, Delegate::class.java)
    }
}


