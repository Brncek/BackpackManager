package com.example.backpackmanager.ui.screens.setingsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.backpackmanager.database.DataRepository
import com.example.backpackmanager.database.Type
import com.example.backpackmanager.ui.screens.commonComponents.TypeUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class SetingsViewModel(private val dataRepository: DataRepository) : ViewModel() {

    val typeUiState: StateFlow<TypeUiState> = dataRepository.getAllTypes().map { TypeUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = TypeUiState()
        )

    suspend fun addType(typeName: String) {
        dataRepository.insert(Type(typeName))
    }

    suspend fun removeType(type : Type) {
        dataRepository.deletedType(type.typeName)
        dataRepository.delete(type)
    }
}