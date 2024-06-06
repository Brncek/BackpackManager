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

    @Query("select distinct groupName from groupItems order by groupName asc")
    fun getGroupsNames() :Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGroup(item: GroupItem)

    @Query("select * from items where id in(select itemId from groupItems where groupName = :groupName)")
    fun getItemsByGroup(groupName: String) : Flow<List<Item>>

    @Query("select id, amount from items it join groupItems gi on(it.id = gi.itemId) " +
            "where id in(select itemId from groupItems where groupName = :groupName)")
    fun getGroupItemsAmounts(groupName: String) : Flow<List<ItemGroupAmount>>

    @Query("Insert into groupItems(itemId, amount, groupName) " +
                "select id, addedToBackpack, :name from items where addedToBackpack > 0 ")
    suspend fun newGroup(name: String)

    @Query("UPDATE items SET addedToBackpack = " +
            "((SELECT amount FROM groupItems WHERE itemId = items.id and groupName = :groupName) + addedToBackpack)" +
            "where id in  (select itemId from groupItems where groupName = :groupName) ")
    suspend fun addGroupToBackpack(groupName: String)

    @Query("Update groupItems set groupName = :newName where groupName = :oldName")
    suspend fun changeGroupName(oldName: String, newName:String)

    @Query("Update groupItems set amount = :amount where groupName = :groupName and itemId = :itemId")
    suspend fun changeGroupItemAmount(groupName: String, itemId: Int, amount:Int)

    @Query("delete from groupItems where groupName = :groupName and itemId = :itemId")
    suspend fun deleteItemFromGroup(groupName: String, itemId: Int)

    @Update
    suspend fun updateGroup(item: GroupItem)
    @Delete
    suspend fun deleteGroup(item: GroupItem)

    @Query("DELETE FROM groupItems WHERE groupName = :name")
    suspend fun deleteGroup(name: String)

    @Query("DELETE FROM groupItems WHERE itemId = :id")
    suspend fun deleteItemFromGroups(id : Int)
}