package com.example.backpackmanager.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "groupItems")
data class GroupItem(
    @PrimaryKey(true)
    val databaseId: Int = 0,
    val groupId: Int = 0,
    val itemId: Int = 0,
    val groupName: String = "",
)