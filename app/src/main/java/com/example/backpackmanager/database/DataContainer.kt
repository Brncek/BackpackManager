package com.example.backpackmanager.database

import android.content.Context

interface DataContainer {
    val dataRepository: DataRepositary
}
class AppDataContainer(private val context: Context) : DataContainer {

    override val dataRepository: DataRepositary by lazy {
        OfflineDataRepository(LocalDatabase.getDatabase(context).itemDao(), LocalDatabase.getDatabase(context).groupItemDao())
    }
}