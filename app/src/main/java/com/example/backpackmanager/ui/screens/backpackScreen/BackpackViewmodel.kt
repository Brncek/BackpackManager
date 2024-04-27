package com.example.backpackmanager.ui.screens.backpackScreen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState

class BackpackViewmodel {
}


data class BackpackUIState(
    val searchString : String = "",
    val itemList : List<String> = listOf()
)
