package com.example.backpackmanager

import android.app.Application
import com.example.backpackmanager.database.AppDataContainer
import com.example.backpackmanager.database.DataContainer

class BackpackManagerApp : Application() {

    lateinit var dataContainer: DataContainer
    override fun onCreate() {
        super.onCreate()
        dataContainer = AppDataContainer(this)
    }
}