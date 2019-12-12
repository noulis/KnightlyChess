package com.example.knighty_chess.data

import com.google.gson.Gson

data class Results (val results: Set<List<Pair<Int, Int>>>)  {

    companion object {
        @JvmStatic
        fun serialize(results: Results) : String {
            val gson = Gson()
            val resultsSerialized: String = gson.toJson(results)
            return resultsSerialized
        }

        @JvmStatic
        fun deSerialize(serializedInput: String) : Results {
            val gson = Gson()
            val resultsDeSerialized = gson.fromJson(serializedInput, Results::class.java)
            return resultsDeSerialized
        }
    }
}