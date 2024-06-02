package com.example.backpackmanager.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.backpackmanager.ui.screens.backpackScreen.BackpackScreen
import com.example.backpackmanager.ui.screens.backpackScreen.BackpackScreenDestination
import com.example.backpackmanager.ui.screens.commonComponents.BottomBar
import com.example.backpackmanager.ui.screens.editingScreen.EditingScreen
import com.example.backpackmanager.ui.screens.editingScreen.ItemEditScreenDestination
import com.example.backpackmanager.ui.screens.groupEditingScreen.EditingGroupScreen
import com.example.backpackmanager.ui.screens.groupEditingScreen.EditingGroupScreenDestination
import com.example.backpackmanager.ui.screens.groupsScreen.GroupScreen
import com.example.backpackmanager.ui.screens.groupsScreen.GroupScreenDestination
import com.example.backpackmanager.ui.screens.itemScreen.ItemScreen
import com.example.backpackmanager.ui.screens.itemScreen.ItemsScreenDestination
import com.example.backpackmanager.ui.screens.setingsScreen.SetingsScreen
import com.example.backpackmanager.ui.screens.setingsScreen.SetingsScreenDestination

@Composable
fun NavigationManager(
    navController: NavHostController
) {

    var selectedItem by remember { mutableIntStateOf(0) }

    NavHost(
        navController = navController,
        startDestination = ItemsScreenDestination.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        composable(route = ItemsScreenDestination.route) {
            selectedItem = 0

            ItemScreen( setingsButtonAction = { navController.navigate(route = SetingsScreenDestination.route) },
                        editorNavigate = { navController.navigate(route = ItemEditScreenDestination.route) },
                        bottomBar = { BottomBar(selectedTab = selectedItem,
                                                firstTabAction = {},
                                                secondTabAction = { navController.navigate(route = BackpackScreenDestination.route)},
                                                thirdTabAction = { navController.navigate(route = GroupScreenDestination.route)} )})
        }

        composable(route = BackpackScreenDestination.route) {
            selectedItem = 1

            BackpackScreen( setingsButtonAction = { navController.navigate(route = SetingsScreenDestination.route) },
                            bottomBar = { BottomBar(selectedTab = selectedItem,
                                                    firstTabAction = { navController.popBackStack(route = ItemsScreenDestination.route, inclusive = false)},
                                                    secondTabAction = {},
                                                    thirdTabAction = { navController.navigate(route = GroupScreenDestination.route)} )})
        }

        composable(route = GroupScreenDestination.route) {
            selectedItem = 2


            GroupScreen(setingsButtonAction =  { navController.navigate(route = SetingsScreenDestination.route) },
                        bottomBar = { BottomBar(selectedTab = selectedItem,
                                                firstTabAction = { navController.popBackStack(route = ItemsScreenDestination.route, inclusive = false)},
                                                secondTabAction = { navController.navigate(route = BackpackScreenDestination.route)},
                                                thirdTabAction = {})},
                        editGroupNavigate = { navController.navigate(route = EditingGroupScreenDestination.route) }
                        )
        }

        composable(route = SetingsScreenDestination.route) {
            SetingsScreen()
        }

        composable(route = ItemEditScreenDestination.route) {
            EditingScreen(onLeave = {navController.popBackStack(route = ItemsScreenDestination.route, inclusive = false)})
        }

        composable(route = EditingGroupScreenDestination.route) {
            EditingGroupScreen(navigateToBackpack = {navController.popBackStack(route = ItemsScreenDestination.route, inclusive = false)
                                               navController.navigate(route = BackpackScreenDestination.route) },
                               navigateBack = {navController.popBackStack(route = GroupScreenDestination.route, inclusive = false)})
        }
    }
}