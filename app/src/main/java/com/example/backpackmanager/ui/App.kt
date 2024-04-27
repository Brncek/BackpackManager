package com.example.backpackmanager.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.backpackmanager.ui.navigation.NavigationManager

@Composable
fun App(navController: NavHostController = rememberNavController()) {
   NavigationManager(navController = navController)
}