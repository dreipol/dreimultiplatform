package ch.dreipol.dreimultiplatform

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

interface PersistentKeyValueStore {
    fun storeString(string: String, forKey: String)
    fun getString(forKey: String): String?
    fun storeBoolean(value: Boolean, forKey: String)
    fun getBoolean(forKey: String): Boolean?
    fun storeInt(value: Int, forKey: String)
    fun getInt(forKey: String): Int?
    fun storeLong(value: Long, forKey: String)
    fun getLong(forKey: String): Long?
    fun storeFloat(value: Float, forKey: String)
    fun getFloat(forKey: String): Float?
    fun removeEntry(forKey: String)
}

inline fun <reified T>PersistentKeyValueStore.storeSerializable(obj: T, forKey: String) {
    val serialized = Json.encodeToString(obj)
    storeString(serialized, forKey)
}

inline fun <reified T>PersistentKeyValueStore.getSerializable(forKey: String): T? =
    getString(forKey)?.let { Json.decodeFromString(it) }