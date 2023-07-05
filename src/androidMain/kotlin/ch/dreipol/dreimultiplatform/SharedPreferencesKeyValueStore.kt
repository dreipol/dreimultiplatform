package ch.dreipol.dreimultiplatform

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

private const val PREFERENCES = "PREFERENCES"

data class SharedPreferencesKeyValueStore(val sharedPreferences: SharedPreferences) : PersistentKeyValueStore {
    constructor(context: Context) : this(context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE))

    override fun storeString(string: String, forKey: String) =
        sharedPreferences.edit {
            putString(forKey, string)
        }

    override fun getString(forKey: String): String? =
        sharedPreferences.getString(forKey, null)

    override fun storeBoolean(value: Boolean, forKey: String) =
        sharedPreferences.edit {
            putBoolean(forKey, value)
        }

    override fun getBoolean(forKey: String): Boolean? =
        if (sharedPreferences.contains(forKey)) sharedPreferences.getBoolean(forKey, false) else null

    override fun storeInt(value: Int, forKey: String) =
        sharedPreferences.edit {
            putInt(forKey, value)
        }

    override fun getInt(forKey: String): Int? =
        if (sharedPreferences.contains(forKey)) sharedPreferences.getInt(forKey, 0) else null

    override fun storeLong(value: Long, forKey: String) =
        sharedPreferences.edit {
            putLong(forKey, value)
        }

    override fun getLong(forKey: String): Long? =
        if (sharedPreferences.contains(forKey)) sharedPreferences.getLong(forKey, 0) else null

    override fun storeFloat(value: Float, forKey: String) =
        sharedPreferences.edit {
            putFloat(forKey, value)
        }

    override fun getFloat(forKey: String): Float? =
        if (sharedPreferences.contains(forKey)) sharedPreferences.getFloat(forKey, 0.0f) else null

    override fun removeEntry(forKey: String) =
        sharedPreferences.edit {
            remove(forKey)
        }
}