package com.example.backpackmanager.ui.screens.itemScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
import com.example.backpackmanager.ui.theme.BackpackManagerTheme
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.random.Random

object ItemsScreenDestination : ScreenDest {
    override val route = "itemsScreen"
}

@Composable
fun ItemScreen(
    viewModel: ItemsViewModel = viewModel(factory = ViewModelCreator.Factory),
    setingsButtonAction: () -> Unit,
    addItemButtonAction: () -> Unit = {},
    bottomBar:  @Composable () -> Unit = {}
    ) {


    val itemsUiState by viewModel.itemsUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold (bottomBar = bottomBar,
              topBar = {TopBar( searchValue = viewModel.searchUiState.search,
                                searchValueOnChange = {viewModel.updateSearchUiState(it)},
                                setingsButtonAction = {setingsButtonAction() } )
                        },
              floatingActionButton = {FloatingActionButton(onClick = { addItemButtonAction() }) {
                  Icon(imageVector = Icons.Default.Add,
                      contentDescription = stringResource(id = R.string.ItemAddButton))
              }}
             )
    { innerPadding -> Column( modifier = Modifier.padding(innerPadding))
        {
            ItemsList(  itemList = itemsUiState.itemList,
                deleteItem = {coroutineScope.launch { viewModel.delete(it) }},
                editItem = {},
                changeEnable = {coroutineScope.launch { viewModel.toggleEnable(it) }},
                search = viewModel.searchUiState.search.lowercase(Locale.getDefault())
            )
        }
    }
}


@Composable
fun ItemsList(
    itemList: List<Item>,
    search: String = "",
    deleteItem: (Item) -> Unit = {},
    editItem: (Item) -> Unit = {},
    changeEnable: (Item) -> Unit = {}
) {
    var show by remember { mutableStateOf(false) } //TODO: View model move
    var shownItem by remember { mutableStateOf(Item()) }


    if (itemList.isEmpty()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp), horizontalArrangement = Arrangement.Absolute.Center) {
            Text(text = stringResource(id = R.string.EmptyList), textAlign = TextAlign.Center, fontSize = 30.sp)
        }
    } else {
        LazyColumn {
            items(items = itemList, key = { it.id }) {
                    item -> if(item.name.lowercase(Locale.getDefault()).contains(search) || search.isBlank()) {
                ItemCard(item = item, showAdded = true,
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
            Button(onClick = {deleteItem(shownItem)
                              show = false
                             }) {
                Text(text = stringResource(id = R.string.DeleteButton))
            }

            Button(onClick = {editItem(shownItem)
                              show = false
                             }, modifier = Modifier.padding(15.dp, 0.dp)) {
                Text(text = stringResource(id = R.string.EditButton))
            }

            Button(onClick = {changeEnable(shownItem)
                              show = false
                             }) {
                if (shownItem.selected == "T") {
                    Text(text = stringResource(id = R.string.ButtonRemove))
                } else {
                    Text(text = stringResource(id = R.string.ButtonAdd))
                }
            }
        }
    }
}


@Preview(showBackground = true)
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun Preview() {
    BackpackManagerTheme {
    }
}