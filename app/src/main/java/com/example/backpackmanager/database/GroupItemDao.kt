package com.example.backpackmanager.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupItemDao {

    @Query("select distinct groupName from groupItems")
    fun getGroupsNames() :Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGroup(item: GroupItem)

    @Query("select * from items where id in(select itemId from groupItems where groupName = :groupName)")
    fun getItemsByGroup(groupName: String) : Flow<List<Item>>

    @Query("Insert into groupItems(itemId, amount, groupName) " +
                "select id, addedToBackpack, :name from items where addedToBackpack > 0 ")
    suspend fun newGroup(name: String)

    @Query("UPDATE items SET addedToBackpack = (SELECT amount FROM groupItems WHERE itemId = items.id and groupName = :groupName) " +
            "where id in  (select itemId from groupItems where groupName = :groupName) ")
    suspend fun addGroupToBackpack(groupName: String)

    @Update
    suspend fun updateGroup(item: GroupItem)
    @Delete
    suspend fun deleteGroup(item: GroupItem)

    @Query("DELETE FROM groupItems WHERE groupName = :name")
    suspend fun deleteGroup(name: String)

    @Query("DELETE FROM groupItems WHERE itemId = :id")
    suspend fun deleteItemFromGroups(id : Int)
}