package com.example.backpackmanager.ui.screens.groupEditingScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.backpackmanager.ui.ViewModelCreator
import com.example.backpackmanager.ui.navigation.ScreenDest

object EditingGroupScreenDestination : ScreenDest {
    override val route = "editingGroupScreen"
}

@Composable
fun EditingGroupScreen (
    viewModel: EditingGroupViewModel = viewModel(factory = ViewModelCreator.Factory),
    navigateBack: () -> Unit,
    navigateToBackpack: () -> Unit
) {
    Text(text = "TEST")
}