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
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.backpackmanager.R
import com.example.backpackmanager.database.Item
import com.example.backpackmanager.ui.ViewModelCreator
import com.example.backpackmanager.ui.navigation.ItemMover
import com.example.backpackmanager.ui.navigation.ScreenDest
import com.example.backpackmanager.ui.screens.commonComponents.DeleteDialog
import com.example.backpackmanager.ui.screens.commonComponents.DetailSheet
import com.example.backpackmanager.ui.screens.commonComponents.ItemCard
import com.example.backpackmanager.ui.screens.commonComponents.TopBar
import com.example.backpackmanager.ui.theme.BackpackManagerTheme
import kotlinx.coroutines.launch
import java.util.Locale

object ItemsScreenDestination : ScreenDest {
    override val route = "itemsScreen"
}

@Composable
fun ItemScreen(
    viewModel: ItemsViewModel = viewModel(factory = ViewModelCreator.Factory),
    setingsButtonAction: () -> Unit,
    editorNavigate: () -> Unit = {},
    bottomBar:  @Composable () -> Unit = {}
    ) {


    val itemsUiState by viewModel.itemsUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    var showAddDialog by remember { mutableStateOf(false) }
    var addedItem by remember { mutableStateOf(Item()) }

    Scaffold (bottomBar = bottomBar,
              topBar = {TopBar( searchValue = viewModel.searchUiState.search,
                                searchValueOnChange = {viewModel.updateSearchUiState(it)},
                                setingsButtonAction = {setingsButtonAction() } )
                        },
              floatingActionButton = {FloatingActionButton(onClick = {ItemMover.item = null
                                                                      editorNavigate() }) {
                  Icon(imageVector = Icons.Default.Add,
                      contentDescription = stringResource(id = R.string.ItemAddButton))
              }}
             )
    { innerPadding -> Column( modifier = Modifier.padding(innerPadding))
        {
            ItemsList(  itemList = itemsUiState.itemList,
                deleteItem = {coroutineScope.launch { viewModel.delete(it) }},
                editItem = {ItemMover.item = it
                             editorNavigate()
                            },
                addItems = { addedItem = it
                             showAddDialog = true
                            },
                removeItem = {coroutineScope.launch { viewModel.removeItem(it)}},
                search = viewModel.searchUiState.search.lowercase(Locale.getDefault()),
            )
        }
    }

    AddDialog(openDialog = showAddDialog, onShowChange = { showAddDialog = false }, confirmAction = {
        coroutineScope.launch { viewModel.addItems(addedItem, it) }
    })
}


@Composable
fun ItemsList(
    itemList: List<Item>,
    search: String = "",
    deleteItem: (Item) -> Unit = {},
    editItem: (Item) -> Unit = {},
    addItems: (Item) -> Unit = {},
    removeItem: (Item) -> Unit = {}
) {
    var show by remember { mutableStateOf(false) } //TODO: View model move
    var shownItem by remember { mutableStateOf(Item()) }
    var showDeleteDialog by remember { mutableStateOf(false) }

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
        Column (horizontalAlignment = Alignment.CenterHorizontally) {
            Row {
                Button(onClick = { show = false
                    showDeleteDialog = true
                }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = stringResource(id = R.string.DeleteButton) , modifier =  Modifier.padding(20.dp, 5.dp))
                }

                Button(onClick = {editItem(shownItem)
                    show = false
                }, modifier = Modifier.padding(15.dp, 0.dp)) {

                    Icon(imageVector = Icons.Default.Edit, contentDescription = stringResource(id = R.string.EditButton) , modifier =  Modifier.padding(20.dp, 5.dp))
                }



                Button(onClick = {addItems(shownItem)
                    show = false
                }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(id = R.string.ButtonRemove) , modifier =  Modifier.padding(20.dp, 5.dp))
                }
            }

            if (shownItem.addedToBackpack > 0) {
                Button(onClick = {removeItem(shownItem)
                    show = false
                }, modifier = Modifier.padding(0.dp, 15.dp)
                ) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = stringResource(id = R.string.ButtonAdd) , modifier =  Modifier.padding(20.dp, 5.dp))
                }
            }
        }
    }

    DeleteDialog(openDialog = showDeleteDialog, onShowChange = { showDeleteDialog = false }, confirmAction = {deleteItem(shownItem)})
}

@Composable
fun AddDialog(
    openDialog: Boolean,
    onShowChange: () ->  Unit,
    confirmAction: (Int) -> Unit,
) {
    var dialogString by remember { mutableStateOf("") }

    if (openDialog) {

        AlertDialog (
            title = {
                Text(text = stringResource(id = R.string.ButtonAdd))
            },

            onDismissRequest = {
                onShowChange()
            },
            confirmButton = {
                TextButton(
                    enabled = validateAddNumber(dialogString),
                    onClick = {
                        onShowChange()
                        confirmAction(dialogString.toInt())
                    }
                ) {
                    Text(stringResource(id = R.string.Confirm))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onShowChange()
                    }
                ) {
                    Text(stringResource(id = R.string.Dismiss))
                }
            },
            text = {
                OutlinedTextField(
                    value = dialogString,
                    onValueChange = {dialogString = it},
                    label = { Text(text = stringResource(id = R.string.Amount) ) },
                    textStyle = TextStyle(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
            }
        )
    }
}


fun validateAddNumber(numberString:String) : Boolean {
    try {
        val test = numberString.toInt()
        return numberString.isNotBlank()
    } catch (e : Exception) {
        return false
    }
}

@Preview(showBackground = true)
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun Preview() {
    BackpackManagerTheme {
    }
}