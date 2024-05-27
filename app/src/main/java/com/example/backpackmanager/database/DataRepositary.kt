package com.example.backpackmanager.database

import kotlinx.coroutines.flow.Flow

interface DataRepositary {
    fun getAllGroupItems(): Flow<List<GroupItem>>

    fun getGroupSearched(search: String): Flow<List<GroupItem>>

    fun getGroupItem(id: Int): Flow<GroupItem>

    suspend fun insertGroup(item: GroupItem)

    suspend fun updateGroup(item: GroupItem)

    suspend fun deleteGroup(item: GroupItem)

    suspend fun deleteGroup(id: Int)

    suspend fun deleteItemFromGroups(id : Int)

    fun getAllItems(): Flow<List<Item>>

    fun getSearched(search: String): Flow<List<Item>>

    fun getSelectedSearched(search: String): Flow<List<Item>>

    fun getSelected(): Flow<List<Item>>

    fun getItem(id: Int): Flow<Item>

    suspend fun insert(item: Item)

    suspend fun update(item: Item)

    suspend fun delete(item: Item)
}