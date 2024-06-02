package com.example.backpackmanager.database

import kotlinx.coroutines.flow.Flow

class OfflineDataRepository(private val itemDao: ItemDao, private val groupItemDao: GroupItemDao, private  val typeDao: TypeDao) :DataRepository {

    override suspend fun updateGroup(item: GroupItem) = groupItemDao.updateGroup(item)

    override suspend fun deleteGroup(item: GroupItem) = groupItemDao.deleteGroup(item)

    override suspend fun deleteGroup(name: String) = groupItemDao.deleteGroup(name)

    override suspend fun deletedType(typeName: String) = itemDao.deletedType(typeName)

    override suspend fun deleteItemFromGroups(id: Int) = groupItemDao.deleteItemFromGroups(id)

    override fun getGroupsNames(): Flow<List<String>> = groupItemDao.getGroupsNames()

    override fun getAllItems(): Flow<List<Item>> = itemDao.getAllItems()

    override fun getSelected(): Flow<List<Item>> = itemDao.getSelected()

    override fun getSelectedItemCountWeight(): Flow<Int> = itemDao.getSelectedItemCountWeight()

    override fun weightsByType(): Flow<List<WeightType>> = itemDao.weightsByType()

    override suspend fun insert(item: Item) = itemDao.insert(item)

    override suspend fun insert(type: Type) = typeDao.insert(type)

    override suspend fun update(item: Item) = itemDao.update(item)

    override suspend fun delete(item: Item) = itemDao.delete(item)

    override suspend fun delete(type: Type) = typeDao.delete(type)

    override fun getAllTypes(): Flow<List<Type>> = typeDao.getAllTypes()

    override fun getItemsByGroup(groupName: String): Flow<List<Item>> = groupItemDao.getItemsByGroup(groupName)

    override suspend fun newGroup(name: String)  = groupItemDao.newGroup(name)

    override suspend fun removeAllItems() = itemDao.removeAllItems()

    override suspend fun addGroupToBackpack(groupName: String) = groupItemDao.addGroupToBackpack(groupName)

    override fun getGroupItemsAmounts(groupName: String): Flow<List<ItemGroupAmount>> = groupItemDao.getGroupItemsAmounts(groupName)

    override suspend fun changeGroupName(oldName: String, newName: String) = groupItemDao.changeGroupName(oldName, newName)

    override suspend fun changeGroupItemAmount(groupName: String, itemId: Int, amount: Int)  = groupItemDao.changeGroupItemAmount(groupName, itemId, amount)

    override suspend fun deleteItemFromGroup(groupName: String, itemId: Int) = groupItemDao.deleteItemFromGroup(groupName, itemId)

}