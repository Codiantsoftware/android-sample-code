package com.example.myapplication.datalayer

/**
 * Abstract class for mapping between two types.
 * @param T1 The type of input data.
 * @param T2 The type of output data.
 */
abstract class Mapper<T1, T2> {

    /**
     * Maps a value of type T1 to type T2.
     * @param value The value to be mapped.
     * @return The mapped value of type T2.
     */
    abstract fun map(value: T1): T2

    /**
     * Maps a value of type T2 back to type T1.
     * @param value The value to be reverse mapped.
     * @return The reverse mapped value of type T1.
     */
    abstract fun reverseMap(value: T2): T1

    /**
     * Maps a list of values of type T1 to type T2.
     * @param values The list of values to be mapped.
     * @return The list of mapped values of type T2.
     */
    fun map(values: List<T1>): List<T2> {
        val returnValues = ArrayList<T2>(values.size)
        for (value in values) {
            returnValues.add(map(value))
        }
        return returnValues
    }

    /**
     * Maps a list of values of type T2 back to type T1.
     * @param values The list of values to be reverse mapped.
     * @return The list of reverse mapped values of type T1.
     *
     * These method will use for reverse mapping in future.
     */
    @Suppress("unused")
    fun reverseMap(values: List<T2>): List<T1> {
        val returnValues = ArrayList<T1>(values.size)
        for (value in values) {
            returnValues.add(reverseMap(value))
        }
        return returnValues
    }
}