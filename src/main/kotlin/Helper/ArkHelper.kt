package io.arkk.arkk


/**
 *
 * Helper object to convert Ark amounts
 *
 */
public object ArkHelper {

    /**
     *
     * Converts to Ark Float amount
     *
     */
    public fun convertToArk(ark: Double): Double {
        return ark / 100000000
    }


    /**
     *
     * Converts to Ark Int amount
     *
     */
    public fun convertFromArk(ark: Double): Double {
        return ark * 100000000
    }
}
