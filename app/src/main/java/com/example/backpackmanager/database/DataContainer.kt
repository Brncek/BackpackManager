package com.example.backpackmanager.database

import android.content.Context

interface DataContainer {
    val dataRepository: DataRepository
}
class AppDataContainer(private val context: Context) : DataContainer {

    override val dataRepository: DataRepository by lazy {
        OfflineDataRepository(LocalDatabase.getDatabase(context).itemDao(), LocalDatabase.getDatabase(context).groupItemDao())
    }
}