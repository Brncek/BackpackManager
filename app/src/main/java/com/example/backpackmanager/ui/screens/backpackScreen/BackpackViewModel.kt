package com.example.backpackmanager.ui.screens.backpackScreen

import androidx.lifecycle.ViewModel

class BackpackViewmodel : ViewModel() {

}

data class BackpackUIState(
    val searchString : String = "",
    val itemList : List<String> = listOf()
)
