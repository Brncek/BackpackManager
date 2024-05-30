package com.example.backpackmanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class, GroupItem::class, Type::class], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun itemDao() :ItemDao
    abstract fun groupItemDao() : GroupItemDao
    abstract  fun  typeDao() : TypeDao

    companion object {
        @Volatile
        private var Instance: LocalDatabase? = null

        fun getDatabase(context: Context): LocalDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, LocalDatabase::class.java, "local_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}

