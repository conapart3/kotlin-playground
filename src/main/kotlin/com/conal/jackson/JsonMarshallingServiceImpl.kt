package com.conal.jackson

import com.fasterxml.jackson.databind.ObjectMapper

class JsonMarshallingServiceImpl(private val mapper: ObjectMapper) : JsonMarshallingService {

    override fun <T> parseJson(input: String, clazz: Class<T>): T {
        return mapper.readValue(input, clazz)
    }

    override fun <T> parseJsonCollection(input: String, clazz: Class<T>): Collection<T> {
        return mapper.readValue(input, mapper.typeFactory.constructCollectionType(Collection::class.java, clazz))
    }
}