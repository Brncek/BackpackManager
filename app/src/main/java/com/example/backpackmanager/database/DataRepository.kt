package com.example.backpackmanager.database

import kotlinx.coroutines.flow.Flow

interface DataRepository {
    fun getAllGroupItems(): Flow<List<GroupItem>>

    fun getGroupSearched(search: String): Flow<List<GroupItem>>

    fun getGroupItem(name: String): Flow<GroupItem>

    suspend fun insertGroup(item: GroupItem)

    suspend fun updateGroup(item: GroupItem)

    suspend fun deleteGroup(item: GroupItem)

    suspend fun deleteGroup(name: String)

    suspend fun deleteItemFromGroups(id: Int)

    fun getGroupsNames() :Flow<List<String>>

    fun getGroupsNamesSearch(search: String) : Flow<List<String>>

    fun getAllItems(): Flow<List<Item>>

    fun getSearched(search: String): Flow<List<Item>>

    fun getSelectedSearched(search: String): Flow<List<Item>>

    fun getSelected(): Flow<List<Item>>

    fun getItem(id: Int): Flow<Item>

    suspend fun insert(item: Item)

    suspend fun update(item: Item)

    suspend fun delete(item: Item)
}