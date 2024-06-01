package com.example.backpackmanager.database

import kotlinx.coroutines.flow.Flow

class OfflineDataRepository(private val itemDao: ItemDao, private val groupItemDao: GroupItemDao, private  val typeDao: TypeDao) :DataRepository {
    override fun getAllGroupItems(): Flow<List<GroupItem>> = groupItemDao.getAllGroupItems()

    override fun getGroupSearched(search: String): Flow<List<GroupItem>> = groupItemDao.getGroupSearched(search)

    override fun getGroupItem(name: String): Flow<GroupItem> = groupItemDao.getGroupItem(name)

    override suspend fun insertGroup(item: GroupItem) = groupItemDao.insertGroup(item)

    override suspend fun updateGroup(item: GroupItem) = groupItemDao.updateGroup(item)

    override suspend fun deleteGroup(item: GroupItem) = groupItemDao.deleteGroup(item)

    override suspend fun deleteGroup(name: String) = groupItemDao.deleteGroup(name)

    override suspend fun deletedType(typeName: String) = itemDao.deletedType(typeName)

    override suspend fun deleteItemFromGroups(id: Int) = groupItemDao.deleteItemFromGroups(id)

    override fun getGroupsNames(): Flow<List<String>> = groupItemDao.getGroupsNames()

    override fun getGroupsNamesSearch(search: String): Flow<List<String>> = groupItemDao.getGroupsNamesSearch(search)

    override fun getAllItems(): Flow<List<Item>> = itemDao.getAllItems()

    override fun getSearched(search: String): Flow<List<Item>> = itemDao.getSearched(search)

    override fun getSelectedSearched(search: String): Flow<List<Item>> = itemDao.getSelectedSearched(search)

    override fun getSelected(): Flow<List<Item>> = itemDao.getSelected()

    override fun getItem(id: Int): Flow<Item> = itemDao.getItem(id)

    override fun getSelectedItemCountWeight(): Flow<Int> = itemDao.getSelectedItemCountWeight()

    override fun weightsByType(): Flow<List<WeightType>> = itemDao.weightsByType()

    override suspend fun insert(item: Item) = itemDao.insert(item)

    override suspend fun insert(type: Type) = typeDao.insert(type)

    override suspend fun update(item: Item) = itemDao.update(item)

    override suspend fun update(type: Type) = typeDao.update(type)

    override suspend fun delete(item: Item) = itemDao.delete(item)

    override suspend fun delete(type: Type) = typeDao.delete(type)

    override fun getAllTypes(): Flow<List<Type>> = typeDao.getAllTypes()

}