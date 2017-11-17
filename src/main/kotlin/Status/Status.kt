package io.arkk.arkk

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

/**
 *
 * Ark Sync Status Data class
 *
 */
public data class Status(

        /**
         *
         * Ark Sync Status ID
         *
         */
        public val id: String,

        /**
         *
         * Is Server syncing
         *
         */
        public val syncing: Boolean,

        /**
         *
         * Height
         *
         */
        public val height: Int
)
{

    class Deserializer : ResponseDeserializable<Status> {
        override fun deserialize(content: String) = Gson().fromJson(content, Status::class.java)
    }
}


