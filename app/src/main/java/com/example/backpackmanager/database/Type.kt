package com.example.backpackmanager.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "types")
data class Type (
    @PrimaryKey(autoGenerate = false)
    val typeName: String = ""
)