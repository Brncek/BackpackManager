package com.example.backpackmanager.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.backpackmanager.BackpackManagerApp
import com.example.backpackmanager.ui.screens.backpackScreen.BackpackViewModel
import com.example.backpackmanager.ui.screens.editingScreen.EditingScreenViewModel
import com.example.backpackmanager.ui.screens.groupEditingScreen.EditingGroupViewModel
import com.example.backpackmanager.ui.screens.groupsScreen.GroupViewModel
import com.example.backpackmanager.ui.screens.itemScreen.ItemsViewModel
import com.example.backpackmanager.ui.screens.setingsScreen.SetingsViewModel

object ViewModelCreator {
    val Factory = viewModelFactory {

        initializer {
            ItemsViewModel(
                backpackManagerApp().dataContainer.dataRepository
            )
        }

        initializer {
            GroupViewModel(
                backpackManagerApp().dataContainer.dataRepository
            )
        }


        initializer {
            EditingScreenViewModel(
                backpackManagerApp().dataContainer.dataRepository
            )
        }


        initializer {
            BackpackViewModel(
                backpackManagerApp().dataContainer.dataRepository
            )
        }

        initializer {
            SetingsViewModel(
                backpackManagerApp().dataContainer.dataRepository
            )
        }

        initializer {
            EditingGroupViewModel(
                backpackManagerApp().dataContainer.dataRepository
            )
        }
    }
}

fun CreationExtras.backpackManagerApp(): BackpackManagerApp =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as BackpackManagerApp)