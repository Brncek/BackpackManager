package com.example.backpackmanager.ui.screens.groupEditingScreen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.backpackmanager.database.DataRepository
import com.example.backpackmanager.database.ItemGroupAmount
import com.example.backpackmanager.ui.navigation.GroupNameMover
import com.example.backpackmanager.ui.screens.commonComponents.ItemsUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class EditingGroupViewModel(private val dataRepository: DataRepository): ViewModel() {
//TODO
    val itemsGroupUiState: StateFlow<ItemsUiState> = dataRepository.getItemsByGroup(GroupNameMover.groupName)
        .map { ItemsUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ItemsUiState()
        )

    val itemsGroupsAmountsUiState: StateFlow<GroupItemAmounts> = dataRepository.getGroupItemsAmounts(GroupNameMover.groupName)
        .map { GroupItemAmounts(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = GroupItemAmounts()
        )

    suspend fun deleteGroup() {
        dataRepository.deleteGroup(GroupNameMover.groupName)
    }

    suspend fun addToBackpack() {
        dataRepository.addGroupToBackpack(GroupNameMover.groupName)
    }

    suspend fun replaceBackpack() {
        dataRepository.removeAllItems()
        dataRepository.addGroupToBackpack(GroupNameMover.groupName)
    }

    suspend fun deleteItemFromGroup(itemId: Int) {
        dataRepository.deleteItemFromGroup(GroupNameMover.groupName, itemId)
    }

    suspend fun changeItemAmount(itemId: Int, amount:Int) {
        dataRepository.changeGroupItemAmount(GroupNameMover.groupName, itemId, amount)
    }

    suspend fun changeGroupName(name:String) {
        dataRepository.changeGroupName(GroupNameMover.groupName, name)
        GroupNameMover.groupName = name
    }
}


data class GroupItemAmounts (
    val itemAmounts : List<ItemGroupAmount> = listOf()
)