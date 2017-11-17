package io.arkk.arkk

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

/**
 *
 * Ark Account Response class to handle data from server
 *
 */
public data class AccountResponse(
        val success: Boolean,
        val account: Account?
)  {

    class Deserializer : ResponseDeserializable<AccountResponse> {
        override fun deserialize(content: String) = Gson().fromJson(content, AccountResponse::class.java)
    }
}

/**
 *
 * Ark Account Data class
 *
 */
public data class Account(

        /**
         *
         * Ark account address
         *
         */
        public val address: String,

        /**
         *
         * Ark account public key
         *
         */
        public val publicKey: String,

        /**
         *
         * Optional number of unconfirmed Ark signatures
         *
         */
        public val unconfirmedSignature: Int?,

        /**
         *
         * Optional Account second signature
         *
         */
        public val secondSignature: Int?,

        /**
         *
         * Optional second public key
         *
         */
        public val secondPublicKey: String?,

        /**
         *
         * Current balance of Ark account
         *
         */
        private val balance: Double,

        /**
         *
         * Current unconfirmed balance of Ark account
         *
         */
        private val unconfirmedBalance: Double

)  {

    class Deserializer : ResponseDeserializable<Account> {
        override fun deserialize(content: String) = Gson().fromJson(content, Account::class.java)
    }

    /**
     *
     * Current balance of Ark account balance
     *
     */
    val arkBalance: Double
        get() = ArkHelper.convertToArk(ark = balance)

    /**
     *
     * Current unconfirmed balance of Ark account
     *
     */
    val unconfirmedArkBalance: Double
        get() = ArkHelper.convertToArk(ark = unconfirmedBalance)
}
