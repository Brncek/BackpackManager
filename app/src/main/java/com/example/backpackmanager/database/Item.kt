package com.example.backpackmanager.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val type: String = "Other",
    val weight: Int = 0,
    val picturePath: String = "",
    val selected: String = "F"
)

//Types
//Other
//Sleep
//Fire
//Shelter
//Food
//Water
//Clothes
//Electronics
//Tools

