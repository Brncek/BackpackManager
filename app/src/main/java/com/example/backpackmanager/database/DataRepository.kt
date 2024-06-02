package com.example.backpackmanager.database

import kotlinx.coroutines.flow.Flow

interface DataRepository {

    suspend fun updateGroup(item: GroupItem)

    suspend fun deleteGroup(item: GroupItem)

    suspend fun deleteGroup(name: String)

    suspend fun deletedType(typeName: String)

    suspend fun deleteItemFromGroups(id: Int)

    fun getGroupsNames() :Flow<List<String>>

    fun getAllItems(): Flow<List<Item>>

    fun getSelected(): Flow<List<Item>>

    fun getSelectedItemCountWeight() : Flow<Int>

    fun weightsByType() : Flow<List<WeightType>>

    suspend fun insert(item: Item)

    suspend fun update(item: Item)

    suspend fun delete(item: Item)

    fun getAllTypes(): Flow<List<Type>>

    suspend fun insert(type: Type)

    suspend fun delete(type: Type)

    fun getItemsByGroup(groupName: String) : Flow<List<Item>>

    suspend fun newGroup(name: String)

    suspend fun removeAllItems()

    suspend fun addGroupToBackpack(groupName: String)

    fun getGroupItemsAmounts(groupName: String) : Flow<List<ItemGroupAmount>>

    suspend fun changeGroupName(oldName: String, newName:String)

    suspend fun changeGroupItemAmount(groupName: String, itemId: Int, amount:Int)

    suspend fun deleteItemFromGroup(groupName: String, itemId: Int)
}