package com.example.backpackmanager.ui.screens.backpackScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.backpackmanager.R
import com.example.backpackmanager.database.Item
import com.example.backpackmanager.ui.ViewModelCreator
import com.example.backpackmanager.ui.navigation.ScreenDest
import com.example.backpackmanager.ui.screens.commonComponents.DetailSheet
import com.example.backpackmanager.ui.screens.commonComponents.ItemCard
import com.example.backpackmanager.ui.screens.commonComponents.TopBar
import kotlinx.coroutines.launch
import java.util.Locale

object BackpackScreenDestination : ScreenDest {
    override val route = "backpackScreen"
}

@Composable
fun BackpackScreen(
    backpackViewModel: BackpackViewModel = viewModel(factory = ViewModelCreator.Factory ),
    setingsButtonAction: () -> Unit,
    bottomBar:  @Composable () -> Unit
) {
    val itemsUiState by backpackViewModel.itemsUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var show by remember { mutableStateOf(false) }

    Scaffold (bottomBar = bottomBar,
        topBar = {
            TopBar( searchValue = backpackViewModel.searchUiState.search,
            searchValueOnChange = {backpackViewModel.updateSearchUiState(it)},
            setingsButtonAction = {setingsButtonAction() } )
        },
        floatingActionButton = {FloatingActionButton(onClick = {show = true}) {
            Icon(imageVector =  Icons.Default.Info, contentDescription = stringResource(id = R.string.ShowStatisticts))
        }}
    )
    { innerPadding -> Column( modifier = Modifier.padding(innerPadding))
        {
            BackpackItemsList(itemList = itemsUiState.itemList,
                              changeEnable = {coroutineScope.launch { backpackViewModel.remove(it) }},
                              search = backpackViewModel.searchUiState.search.lowercase(Locale.getDefault())
                            )
        }
    }
}

@Composable
fun infoSheet(
    show: Boolean,
    onShowChange: () -> Unit,
    itemList: List<Item>
) {

}


@Composable
fun BackpackItemsList(
    itemList: List<Item>,
    changeEnable: (Item) -> Unit = {},
    search: String = "",
) {
    var show by remember { mutableStateOf(false) }
    var shownItem by remember { mutableStateOf(Item()) }


    if (itemList.isEmpty()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp), horizontalArrangement = Arrangement.Absolute.Center) {
            Text(text = stringResource(id = R.string.BackpackEmpty), textAlign = TextAlign.Center, fontSize = 30.sp)
        }
    } else {
        LazyColumn {
            items(items = itemList, key = { it.id }) {
                    item -> if(item.name.lowercase(Locale.getDefault()).contains(search) || search.isBlank()) {
                ItemCard(item = item, showAdded = false,
                    modifier = Modifier
                        .padding(10.dp)
                        .clickable {
                            shownItem = item
                            show = true
                        }
                )
            }
            }
        }
    }

    DetailSheet(show = show, onChangeShow = {show = false},
        item = shownItem
    ) {
        Row {
            Button(onClick = {changeEnable(shownItem)
                show = false
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)) {
                Text(text = stringResource(id = R.string.ButtonRemove))
            }
        }
    }
}