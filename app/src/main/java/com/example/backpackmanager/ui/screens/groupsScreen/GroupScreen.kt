package com.example.backpackmanager.ui.screens.groupsScreen


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.backpackmanager.R
import com.example.backpackmanager.ui.ViewModelCreator
import com.example.backpackmanager.ui.navigation.GroupNameMover
import com.example.backpackmanager.ui.navigation.ScreenDest
import com.example.backpackmanager.ui.screens.commonComponents.TopBar
import java.util.Locale

object GroupScreenDestination : ScreenDest {
    override val route = "groupScreen"
}


@Composable
fun GroupScreen(
    groupViewModel: GroupViewModel = viewModel(factory = ViewModelCreator.Factory),
    setingsButtonAction: () -> Unit,
    editGroupNavigate: () -> Unit,
    bottomBar:  @Composable () -> Unit
    ) {
    val itemGroupsUiState by groupViewModel.itemGroupsUiState.collectAsState()

    Scaffold (bottomBar = bottomBar,
            topBar = {
                TopBar( searchValue = groupViewModel.searchUiState.search,
                searchValueOnChange = {groupViewModel.updateSearchUiState(it)},
                setingsButtonAction = {setingsButtonAction() } )
            }
        ) { innerPadding -> Column( modifier = Modifier.padding(innerPadding))
            {
                GroupsList(listOfNames = itemGroupsUiState.groupItemList,
                    search = groupViewModel.searchUiState.search,
                    editGroupNavigate = { editGroupNavigate() })
            }
    }
}

@Composable
fun GroupsList(
    listOfNames: List<String>,
    editGroupNavigate: () -> Unit,
    search :String
) {
    if (listOfNames.isEmpty()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp), horizontalArrangement = Arrangement.Absolute.Center) {
            Text(text = stringResource(id = R.string.NoGroups), textAlign = TextAlign.Center, fontSize = 30.sp)
        }
    } else {
        LazyColumn {
            items(items = listOfNames, key = { it }) {
                item -> if(item.lowercase(Locale.getDefault()).contains(search) || search.isBlank()) {
                    GroupItemCard(name = item,
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable {
                                GroupNameMover.groupName = item
                                editGroupNavigate()
                            }
                    )
                }
            }
        }
    }
}

@Composable
fun GroupItemCard(
    name : String,
    modifier: Modifier
) {
    Card( modifier = modifier, elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)) {
        Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .height(70.dp)
                .fillMaxWidth())
        {
            Text(text = name , fontSize = 25.sp, fontWeight = FontWeight.ExtraBold , textAlign = TextAlign.Center)
        }
    }
}