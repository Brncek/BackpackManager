package com.example.backpackmanager.database

import kotlinx.coroutines.flow.Flow

class OfflineDataRepository(private val itemDao: ItemDao, private val groupItemDao: GroupItemDao) :DataRepositary {
    override fun getAllGroupItems(): Flow<List<GroupItem>> = groupItemDao.getAllGroupItems()

    override fun getGroupSearched(search: String): Flow<List<GroupItem>> = groupItemDao.getGroupSearched(search)

    override fun getGroupItem(id: Int): Flow<GroupItem> = groupItemDao.getGroupItem(id)

    override suspend fun insertGroup(item: GroupItem) = groupItemDao.insertGroup(item)

    override suspend fun updateGroup(item: GroupItem) = groupItemDao.updateGroup(item)

    override suspend fun deleteGroup(item: GroupItem) = groupItemDao.deleteGroup(item)

    override suspend fun deleteGroup(id: Int) = groupItemDao.deleteItemFromGroups(id)

    override suspend fun deleteItemFromGroups(id: Int) = groupItemDao.deleteItemFromGroups(id)

    override fun getAllItems(): Flow<List<Item>> = itemDao.getAllItems()

    override fun getSearched(search: String): Flow<List<Item>> = itemDao.getSearched(search)

    override fun getSelectedSearched(search: String): Flow<List<Item>> = itemDao.getSelectedSearched(search)

    override fun getSelected(): Flow<List<Item>> = itemDao.getSelected()

    override fun getItem(id: Int): Flow<Item> = itemDao.getItem(id)

    override suspend fun insert(item: Item) = itemDao.insert(item)

    override suspend fun update(item: Item) = itemDao.update(item)

    override suspend fun delete(item: Item) = itemDao.delete(item)

}