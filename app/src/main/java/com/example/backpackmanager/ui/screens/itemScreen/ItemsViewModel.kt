package com.example.backpackmanager.ui.screens.itemScreen

import androidx.lifecycle.ViewModel

class ItemsViewModel : ViewModel() {

}


data class ItemsUIState (
    val itemList : List<String> = listOf()
)