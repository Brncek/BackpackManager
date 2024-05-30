package com.example.backpackmanager.ui.screens.commonComponents

import com.example.backpackmanager.database.Item
import com.example.backpackmanager.database.Type

data class ItemsUiState(val itemList: List<Item> = listOf())
data class TypeUiState(val typeList: List<Type> = listOf())
data class SearchUiState(val search: String = "")