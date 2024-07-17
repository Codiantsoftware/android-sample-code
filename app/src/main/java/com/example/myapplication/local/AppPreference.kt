@file:Suppress("unused")

package com.example.myapplication.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.myapplication.util.isStringNullOrBlank


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object AppPreference {

    private const val USER_PREFERENCES_NAME = "com.example.myapplication"

    private val Context.dataStore by preferencesDataStore(
        name = USER_PREFERENCES_NAME
    )
    private var dataStore: DataStore<Preferences>? = null

    /**
     * Create DataStore with Shared Preference Migration
     * */
    fun getInstance(context: Context) {
        dataStore = context.dataStore
    }

    /**
     * Store value in DataStore
     *
     * @param key The key under which the value will be stored.
     * @param value The value to be stored.
     */
    private suspend inline fun <reified T> storeValue(key: Preferences.Key<T>, value: Any) {
        dataStore!!.edit {
            it[key] = value as T
        }
    }

    /**
     * Read value from DataStore
     *
     * @param key The key from which the value will be read.
     * @return The value associated with the key, or null if no value is present.
     */
    private inline fun <reified T> readValue(key: Preferences.Key<T>): T? {

        return runBlocking {
            dataStore!!.data.first()[key]
        }


    }

    /**
     * Clear all values from DataStore.
     */
    private fun clearDataStore() {
        runBlocking {
            dataStore!!.edit {
                /*
                * Clear Preference
                * */
                it.clear()

            }

        }
    }

    /**
     * add string value
     */
    private fun addValue(key: Preferences.Key<String>, value: String) {
        runBlocking {
            storeValue(key, value)
        }
    }

    /**
     * add int value
     */
    fun addInt(key: Preferences.Key<Int>, value: Int) {
        runBlocking {
            storeValue(key, value)
        }
    }

    /**
     * add boolean value
     */
    fun addBoolean(key: Preferences.Key<Boolean>, value: Boolean) {
        runBlocking {
            storeValue(key, value)
        }
    }

    /**
     * add float value
     */
    fun addFloat(key: Preferences.Key<Float>, value: Float) {
        runBlocking {
            storeValue(key, value)
        }
    }

    /**
     * add long value
     */
    fun addLong(key: Preferences.Key<Long>, value: Long) {
        runBlocking {
            storeValue(key, value)
        }
    }

    /**
     * get string value
     */
    fun getValue(key: Preferences.Key<String>): String {
        val value = readValue(key)
        return if (value.isStringNullOrBlank()) {
            ""
        } else {
            value!!
        }
    }

    /**
     * get int value
     */
    fun getInt(key: Preferences.Key<Int>): Int {
        return try {
            readValue(key) as Int
        } catch (e: Exception) {
            0
        }
    }

    /**
     * get boolean value
     */
    fun getBoolean(key: Preferences.Key<Boolean>, default: Boolean = false): Boolean {
        return try {
            readValue(key) as Boolean
        } catch (e: Exception) {
            default
        }
    }

    /**
     * get float value
     */
    fun getFloat(key: Preferences.Key<Float>): Float {
        return try {
            readValue(key) as Float
        } catch (e: Exception) {
            0f
        }
    }

    /**
     * get long value
     */
    fun getLong(key: Preferences.Key<Long>): Long {
        return try {
            readValue(key) as Long
        } catch (e: Exception) {
            0
        }
    }

    /**
     * Convert the object to a JSON string and store it in DataStore.
     */
    fun putByGSON(key: Preferences.Key<String>, any: Any?) {
        val gson = Gson()
        val json = gson.toJson(any)
        addValue(key, json)
    }

    /**
     * Convert the object to a JSON string and store it in DataStore.
     */
    fun <T> put(t: T, key: Preferences.Key<String>) {
        //Convert object to JSON String.
        val jsonString = GsonBuilder().create().toJson(t)

        addValue(key, jsonString)
    }


    /**
     * Retrieve an object of type T from DataStore and convert it from a JSON string.
     */
    inline fun <reified T> get(key: Preferences.Key<String>): T? {
        //We read JSON String which was saved.
        val value = getValue(key)
        //JSON String was found which means object can be read.
        //We convert this JSON String to model object. Parameter "c" (of
        //type “T” is used to cast.
        return GsonBuilder().create().fromJson(value, T::class.java)
    }

    /**
    * Retrieve an ArrayList of Any type from DataStore.
    */
    fun getArrayList(key: Preferences.Key<String>): ArrayList<Any>? {
        val gson = Gson()
        val json: String = getValue(key)
        val type = object : TypeToken<ArrayList<Any?>?>() {}.type
        return gson.fromJson<ArrayList<Any>>(json, type)
    }


    fun clearSharedPreference() {
        clearDataStore()
    }


}