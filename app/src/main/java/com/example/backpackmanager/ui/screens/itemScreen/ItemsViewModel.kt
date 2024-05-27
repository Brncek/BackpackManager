package com.example.backpackmanager.ui.screens.itemScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.backpackmanager.database.DataRepositary
import com.example.backpackmanager.database.Item
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ItemsViewModel(private val dataRepositary: DataRepositary) : ViewModel() {
    var searchUiState by mutableStateOf(SearchUiState())
        private set

    val itemsUiState: StateFlow<ItemsUiState> = dataRepositary.getAllItems().map { ItemsUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ItemsUiState()
        )

    var itemsUiStateSearch: StateFlow<ItemsUiState> = dataRepositary.getSearched(searchUiState.serach).map { ItemsUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ItemsUiState()
        )
}

data class ItemsUiState(val itemList: List<Item> = listOf())
data class SearchUiState(val serach: String = "")
