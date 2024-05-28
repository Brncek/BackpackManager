package com.example.backpackmanager.ui.screens.commonComponents

import com.example.backpackmanager.database.Item

data class ItemsUiState(val itemList: List<Item> = listOf())
data class SearchUiState(val search: String = "")