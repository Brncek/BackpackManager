package com.example.backpackmanager.ui.screens.itemScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.backpackmanager.database.DataRepository
import com.example.backpackmanager.database.Item
import com.example.backpackmanager.ui.screens.commonComponents.ItemsUiState
import com.example.backpackmanager.ui.screens.commonComponents.SearchUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ItemsViewModel(private val dataRepositary: DataRepository) : ViewModel() {
    var searchUiState by mutableStateOf(SearchUiState())
        private set

    var itemsUiState: StateFlow<ItemsUiState> = dataRepositary.getAllItems().map { ItemsUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ItemsUiState()
        )

    fun updateSearchUiState(search: String) {
        searchUiState = SearchUiState(search = search)
    }

    suspend fun delete(item : Item) {
        dataRepositary.deleteItemFromGroups(item.id)
        dataRepositary.delete(item)
    }

    suspend fun toggleEnable(item: Item) {
        var newItem: Item
        if (item.selected == "T") {
            newItem = item.copy(selected = "F")
        } else {
            newItem = item.copy(selected = "T")
        }

        dataRepositary.update(newItem)
    }
}


