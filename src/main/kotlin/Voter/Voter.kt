package io.arkk.arkk

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson


/**
 *
 * Ark Voters Response class to handle data from server
 *
 */
public data class VotersResponse(
        val success: Boolean,
        val accounts: Array<Voter>?
)  {

    class Deserializer : ResponseDeserializable<VotersResponse> {
        override fun deserialize(content: String) = Gson().fromJson(content, VotersResponse::class.java)
    }
}


/**
 *
 * Ark Voter Data class
 *
 */
public data class Voter(

        /**
         *
         * Ark voter username
         *
         */
        public val username: String?,

        /**
         *
         * Ark voter address
         *
         */
        public val address: String,

        /**
         *
         * Ark voter public key
         *
         */
        public val publicKey: String,

        /**
         *
         * Number of produced blocks
         *
         */
        public val producedblocks: Int,

        /**
         *
         * Ark voter balance
         *
         */
        private val balance: Double
)
{

    class Deserializer : ResponseDeserializable<Voter> {
        override fun deserialize(content: String) = Gson().fromJson(content, Voter::class.java)
    }


    /**
     *
     * Ark voter balance
     *
     */
    public val arkBalance: Double
        get() = ArkHelper.convertToArk(ark = balance)
}


