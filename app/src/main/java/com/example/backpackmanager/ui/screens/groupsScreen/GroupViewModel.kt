package com.example.backpackmanager.ui.screens.groupsScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.backpackmanager.database.DataRepository
import com.example.backpackmanager.database.GroupItem
import com.example.backpackmanager.database.Item
import com.example.backpackmanager.ui.screens.commonComponents.ItemsUiState
import com.example.backpackmanager.ui.screens.commonComponents.SearchUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class GroupViewModel(private val dataRepositary: DataRepository) : ViewModel() {
    var searchUiState by mutableStateOf(SearchUiState())
        private set

    val ItemGroupsUiState: StateFlow<ItemGroupsUiState> = dataRepositary.getAllGroupItems().map { ItemGroupsUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ItemGroupsUiState()
        )

    var ItemGroupsUiStateSearch: StateFlow<ItemGroupsUiState> = dataRepositary.getGroupSearched(searchUiState.search).map { ItemGroupsUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ItemGroupsUiState()
        )

    fun updateSearchUiState(search: String) {
        searchUiState = SearchUiState(search = search)
    }
}

data class ItemGroupsUiState (val groupItemList: List<GroupItem> = listOf())