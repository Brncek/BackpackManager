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
    @Query("Select * from groupItems order by groupName asc")
    fun getAllGroupItems(): Flow<List<GroupItem>>

    @Query("Select * from groupItems WHERE instr(groupName, :search) > 0 order by groupName asc")
    fun getGroupSearched(search: String): Flow<List<GroupItem>>

    @Query("Select * from groupItems where groupId = :id")
    fun getGroupItem(id: Int): Flow<GroupItem>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGroup(item: GroupItem)

    @Update
    suspend fun updateGroup(item: GroupItem)
    @Delete
    suspend fun deleteGroup(item: GroupItem)

    @Query("DELETE FROM groupItems WHERE groupId = :id")
    suspend fun deleteGroup(id: Int)

    @Query("DELETE FROM groupItems WHERE itemId = :id")
    suspend fun deleteItemFromGroups(id : Int)
}