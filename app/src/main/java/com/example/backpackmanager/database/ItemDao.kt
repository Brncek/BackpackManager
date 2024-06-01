package com.example.backpackmanager.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.util.Dictionary

@Dao
interface ItemDao {
    @Query("Select * from items order by name asc")
    fun getAllItems(): Flow<List<Item>>

    @Query("Select * from items WHERE instr(name, :search) > 0 order by name asc")
    fun getSearched(search: String): Flow<List<Item>>

    @Query("Select * from items WHERE instr(name, :search) > 0 and addedToBackpack > 0 order by name asc")
    fun getSelectedSearched(search: String): Flow<List<Item>>

    @Query("Select * from items WHERE addedToBackpack > 0 order by name asc")
    fun getSelected(): Flow<List<Item>>

    @Query("Select * from items where id == :id")
    fun getItem(id: Int): Flow<Item>

    @Query("UPDATE items SET type = 'Other' WHERE type = :typeName")
    suspend fun deletedType(typeName: String)

    @Query("select sum(addedToBackpack * weight) from items where addedToBackpack > 0")
    fun getSelectedItemCountWeight() : Flow<Int>

    @Query("select type , sum(addedToBackpack * weight) as totalWeight from items where addedToBackpack > 0 group by type")
    fun weightsByType() : Flow<List<WeightType>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)
}
