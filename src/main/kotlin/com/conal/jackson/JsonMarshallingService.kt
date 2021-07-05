package com.conal.jackson

/**
 * An optional service CorDapps and other services may use to marshall arbitrary content in and out of JSON format using standard/approved
 * mappers.
 */
interface JsonMarshallingService {
    /**
     * Deserializes the [input] JSON into an instance of [T].
     *
     * @param input The JSON to deserialize.
     * @param clazz The [Class] type to deserialize into.
     *
     * @return A new instance of [T].
     */
    fun <T> parseJson(input: String, clazz: Class<T>): T

    /**
     * Deserializes the [input] JSON into a collection of instances of [T].
     *
     * @param input The JSON to deserialize.
     * @param clazz The [Class] type to deserialize into.
     *
     * @return A new instance of [T].
     */
    fun <T> parseJsonCollection(input: String, clazz: Class<T>): Collection<T>
}

/**
 * Deserializes the [input] JSON into an instance of [T].
 *
 * @param input The JSON to deserialize.
 *
 * @return A new instance of [T].
 */
inline fun <reified T> JsonMarshallingService.parseJson(input: String): T {
    return parseJson(input, T::class.java)
}

/**
 * Deserializes the [input] JSON into a collection of instances of [T].
 *
 * @param input The JSON to deserialize.
 *
 * @return A new instance of [T].
 */
inline fun <reified T> JsonMarshallingService.parseJsonCollection(input: String): Collection<T> {
    return parseJsonCollection(input, T::class.java)
}