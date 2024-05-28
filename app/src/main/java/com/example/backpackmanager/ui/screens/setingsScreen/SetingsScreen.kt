package com.example.backpackmanager.ui.screens.setingsScreen

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.backpackmanager.ui.navigation.ScreenDest


object SetingsScreenDestination : ScreenDest {
    override val route = "setingsScreen"
}

@Composable
fun SetingsScreen( setingsViewModel : SetingsViewModel = viewModel()) {

}
