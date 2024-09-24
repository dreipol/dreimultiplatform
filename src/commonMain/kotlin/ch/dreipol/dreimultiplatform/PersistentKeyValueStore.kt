package ch.dreipol.dreimultiplatform

import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

interface PersistentKeyValueStore {
    fun storeString(
        string: String,
        forKey: String,
    )

    fun getString(forKey: String): String?

    fun storeBoolean(
        value: Boolean,
        forKey: String,
    )

    fun getBoolean(forKey: String): Boolean?

    fun storeInt(
        value: Int,
        forKey: String,
    )

    fun getInt(forKey: String): Int?

    fun storeLong(
        value: Long,
        forKey: String,
    )

    fun getLong(forKey: String): Long?

    fun storeFloat(
        value: Float,
        forKey: String,
    )

    fun getFloat(forKey: String): Float?

    fun removeEntry(forKey: String)
}

class InMemoryKeyValueStore: PersistentKeyValueStore {
    private val storage = mutableMapOf<String, Any>()
    override fun storeString(string: String, forKey: String) {
        storage[forKey] = string
    }

    override fun getString(forKey: String): String? =
        storage[forKey] as? String

    override fun storeBoolean(value: Boolean, forKey: String) {
        storage[forKey] = value
    }

    override fun getBoolean(forKey: String): Boolean? =
        storage[forKey] as? Boolean

    override fun storeInt(value: Int, forKey: String) {
        storage[forKey] = value
    }

    override fun getInt(forKey: String): Int? =
        storage[forKey] as? Int

    override fun storeLong(value: Long, forKey: String) {
        storage[forKey] = value
    }

    override fun getLong(forKey: String): Long? =
        storage[forKey] as? Long

    override fun storeFloat(value: Float, forKey: String) {
        storage[forKey] = value
    }

    override fun getFloat(forKey: String): Float? =
        storage[forKey] as? Float

    override fun removeEntry(forKey: String) {
        storage.remove(forKey)
    }
}

@Throws(SerializationException::class, IllegalArgumentException::class)
inline fun <reified T> PersistentKeyValueStore.storeSerializable(
    obj: T,
    forKey: String,
) {
    val serialized = Json.encodeToString(obj)
    storeString(serialized, forKey)
}

inline fun <reified T> PersistentKeyValueStore.getSerializable(forKey: String): T? = getString(forKey)?.let {
    runCatching {
        Json.decodeFromString<T>(it)
    }.getOrNull()
}