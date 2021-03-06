package com.ivolunteer.ivolunteer.resources

import java.io.FileOutputStream
import java.util.*
import java.util.concurrent.ConcurrentHashMap

enum class StorageTypes{
    USER_NAME,
    IS_VOLUNTEER,
    USER_ID,
    TOKEN,
    CITIES_LIST,
  TYPES_LIST,
    RATE_ID,
    USER_TYPE,
    SHARED_PREFERENCE,
    IS_FIRST_TIME
}

class StorageManager {
    var store : ConcurrentHashMap<String, Any> = ConcurrentHashMap()

    fun <T>get(key: String) : T? {
        if(!store.containsKey(key)) {
            return null
        }

        return store[key] as T?
    }

    fun set(key: String, obj: Any) {
        store.put(key, obj)
    }

    companion object {
        val instance = StorageManager()
    }
}