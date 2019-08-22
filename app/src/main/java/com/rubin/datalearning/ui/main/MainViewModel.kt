package com.rubin.datalearning.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.rubin.datalearning.data.MonsterRepository
import com.rubin.datalearning.utilities.LOG_TAG

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val dataRepo = MonsterRepository()

    init {
        val monsterData = dataRepo.getMonsterData(app)
        for (monster in monsterData) {
            Log.i(LOG_TAG, "${monster.name} (\$${monster.price})")
        }
    }
}