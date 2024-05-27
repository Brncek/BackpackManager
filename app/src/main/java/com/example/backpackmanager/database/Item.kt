package com.example.backpackmanager.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val type: String = "",
    val weight: Int = 0,
    val picturePath: String = "",
    val selected: Char = 'F'
)

