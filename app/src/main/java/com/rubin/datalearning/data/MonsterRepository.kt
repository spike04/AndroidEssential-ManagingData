package com.rubin.datalearning.data

import android.content.Context
import com.rubin.datalearning.utilities.FileHelper
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class MonsterRepository {

    private val listType = Types.newParameterizedType(
        List::class.java,
        Monster::class.java
    )

    fun getMonsterData(context: Context): List<Monster> {
        val text = FileHelper.getTextFromAssets(context, "monster_data.json")
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val adapter: JsonAdapter<List<Monster>> = moshi.adapter(listType)

        return adapter.fromJson(text) ?: emptyList()


    }
}