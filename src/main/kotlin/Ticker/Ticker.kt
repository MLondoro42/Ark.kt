package io.arkk.arkk

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson


/**
 *
 * Ark Ticker Data class
 *
 */
public data class Ticker(

        /**
         *
         * Australian Dollar
         *
         */
        val AUD : Double,

        /**
         *
         * Brazilian Real
         *
         */
        val BRL : Double,

        /**
         *
         * Bitcoin
         *
         */
        val BTC : Double,

        /**
         *
         * Canadian Dollar
         *
         */
        val CAD : Double,

        /**
         *
         * Chilean Peso
         *
         */
        val CLP : Double,

        /**
         *
         * Yuan Renminbi
         *
         */
        val CNY : Double,

        /**
         *
         * Czech Koruna
         *
         */
        val CZK : Double,

        /**
         *
         * Danish Krone
         *
         */
        val DKK : Double,

        /**
         *
         * Euro
         *
         */
        val EUR : Double,

        /**
         *
         * British Pound Sterling
         *
         */
        val GBP : Double,

        /**
         *
         * Hong Kong Dollar
         *
         */
        val HKD : Double,

        /**
         *
         * Indonesian Rupiah
         *
         */
        val IDR : Double,

        /**
         *
         * Indian Rupee
         *
         */
        val INR : Double,

        /**
         *
         * Japanese Yen
         *
         */
        val JPY : Double,

        /**
         *
         * Korean Won
         *
         */
        val KRW : Double,

        /**
         *
         * Mexican Nuevo Peso
         *
         */
        val MXN : Double,

        /**
         *
         * Norwegian Krone
         *
         */
        val NOK : Double,

        /**
         *
         * New Zealand Dollar
         *
         */
        val NZD : Double,

        /**
         *
         * Philippine Peso
         *
         */
        val PHP : Double,

        /**
         *
         * Pakistan Rupee
         *
         */
        val PKR : Double,

        /**
         *
         * Russian Ruble
         *
         */
        val RUB : Double,

        /**
         *
         * Swedish Krona
         *
         */
        val SEK : Double,

        /**
         *
         * Thai Baht
         *
         */
        val THB : Double,

        /**
         *
         * Taiwan Dollar
         *
         */
        val TWD : Double,

        /**
         *
         * US Dollar
         *
         */
        val USD : Double
)
{

    class Deserializer : ResponseDeserializable<Ticker> {
        override fun deserialize(content: String) = Gson().fromJson(content, Ticker::class.java)
    }
}


