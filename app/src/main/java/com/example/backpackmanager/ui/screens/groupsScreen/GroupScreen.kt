package com.example.backpackmanager.ui.screens.groupsScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.backpackmanager.ui.ViewModelCreator
import com.example.backpackmanager.ui.navigation.ScreenDest
import com.example.backpackmanager.ui.screens.commonComponents.TopBar

object GroupScreenDestination : ScreenDest {
    override val route = "groupScreen"
}


@Composable
fun GroupScreen(
    groupViewModel: GroupViewModel = viewModel(factory = ViewModelCreator.Factory),
    setingsButtonAction: () -> Unit,
    bottomBar:  @Composable () -> Unit
    ) {

        Scaffold (bottomBar = bottomBar,
            topBar = {
                TopBar( searchValue = groupViewModel.searchUiState.search,
                searchValueOnChange = {groupViewModel.updateSearchUiState(it)},
                setingsButtonAction = {setingsButtonAction() } )
            }
        )
        { innerPadding -> Column( modifier = Modifier.padding(innerPadding))
            {
                //TODO::
            }
        }
}