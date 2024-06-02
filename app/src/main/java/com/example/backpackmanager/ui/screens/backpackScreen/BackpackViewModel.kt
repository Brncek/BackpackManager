package com.example.backpackmanager.ui.screens.backpackScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.backpackmanager.database.DataRepository
import com.example.backpackmanager.database.Item
import com.example.backpackmanager.database.WeightType
import com.example.backpackmanager.ui.screens.commonComponents.ItemsUiState
import com.example.backpackmanager.ui.screens.commonComponents.SearchUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class BackpackViewModel(private val dataRepository: DataRepository) : ViewModel() {
    var searchUiState by mutableStateOf(SearchUiState())
        private set

    val itemsUiState: StateFlow<ItemsUiState> = dataRepository.getSelected().map { ItemsUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ItemsUiState()
        )


    val allWeights: StateFlow<Int> = dataRepository.getSelectedItemCountWeight().map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = 0
        )

    val weightsTypes: StateFlow<ItemWeightsUiState> = dataRepository.weightsByType().map {ItemWeightsUiState(it)}
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ItemWeightsUiState()
        )

    fun updateSearchUiState(search: String) {
        searchUiState = SearchUiState(search = search)
    }

    suspend fun remove(item: Item) {
        val newItem =  item.copy(addedToBackpack = 0)
        dataRepository.update(newItem)
    }

    suspend fun createNewGroup(name: String) {
        dataRepository.newGroup(name)
    }
}

data class ItemWeightsUiState(
    val typeWeightList : List<WeightType> = listOf()
)


