package ch.dreipol.dreimultiplatform

import platform.Foundation.NSUserDefaults

data class UserDefaultsKeyValueStore(val userDefaults: NSUserDefaults) : PersistentKeyValueStore {
    constructor(): this(NSUserDefaults.standardUserDefaults())

    override fun storeString(string: String, forKey: String) {
        userDefaults.setObject(string, forKey)
    }

    override fun getString(forKey: String): String? =
        userDefaults.stringForKey(forKey)

    override fun storeBoolean(value: Boolean, forKey: String) {
        userDefaults.setBool(value, forKey)
    }

    override fun getBoolean(forKey: String): Boolean? =
        if (userDefaults.objectForKey(forKey) == null) null else userDefaults.boolForKey(forKey)

    override fun storeInt(value: Int, forKey: String) {
        userDefaults.setInteger(value.toLong(), forKey)
    }

    override fun getInt(forKey: String): Int? =
        if (userDefaults.objectForKey(forKey) == null) null else userDefaults.integerForKey(forKey).toInt()

    override fun storeLong(value: Long, forKey: String) {
        userDefaults.setInteger(value, forKey)
    }

    override fun getLong(forKey: String): Long? =
        if (userDefaults.objectForKey(forKey) == null) null else userDefaults.integerForKey(forKey)

    override fun storeFloat(value: Float, forKey: String) {
        userDefaults.setFloat(value, forKey)
    }

    override fun getFloat(forKey: String): Float? =
        if (userDefaults.objectForKey(forKey) == null) null else userDefaults.floatForKey(forKey)

    override fun removeEntry(forKey: String) {
        userDefaults.removeObjectForKey(forKey)
    }
}