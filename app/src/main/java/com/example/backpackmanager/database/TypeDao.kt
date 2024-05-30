package com.example.backpackmanager.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TypeDao {

    @Query("Select * from types order by typeName asc")
    fun getAllTypes(): Flow<List<Type>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(type: Type)

    @Update
    suspend fun update(type: Type)

    @Delete
    suspend fun delete(type: Type)
}
