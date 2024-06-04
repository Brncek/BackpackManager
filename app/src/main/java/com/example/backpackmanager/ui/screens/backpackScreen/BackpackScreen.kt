package com.example.backpackmanager.ui.screens.backpackScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import com.example.backpackmanager.database.WeightType
import com.example.backpackmanager.ui.ViewModelCreator
import com.example.backpackmanager.ui.navigation.ScreenDest
import com.example.backpackmanager.ui.screens.commonComponents.DetailSheet
import com.example.backpackmanager.ui.screens.commonComponents.GetTextDialog
import com.example.backpackmanager.ui.screens.commonComponents.ItemCard
import com.example.backpackmanager.ui.screens.commonComponents.TopBar
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.math.roundToInt

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
    val allItemsWeight by backpackViewModel.allWeights.collectAsState()
    val weightsUiState by backpackViewModel.weightsTypes.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var show by remember { mutableStateOf(false) }
    var showGroupAdd by remember { mutableStateOf(false) }

    Scaffold (bottomBar = bottomBar,
        topBar = {
            TopBar( searchValue = backpackViewModel.searchUiState.search,
            searchValueOnChange = { backpackViewModel.updateSearchUiState(it)},
            setingsButtonAction = { setingsButtonAction() },
            leadingButton = { if (allItemsWeight > 0) {
                                    IconButton(onClick = { showGroupAdd = true }, modifier = Modifier.fillMaxHeight()) {
                                        Icon(imageVector = Icons.Default.Favorite, contentDescription = stringResource(
                                            id = R.string.createGroups
                                        ))
                                    }
                                }
                            }    
            )
        },
        floatingActionButton = {
            if (allItemsWeight > 0) {
                FloatingActionButton(onClick = {show = true}) {
                    Icon(imageVector =  Icons.Default.Info, contentDescription = stringResource(id = R.string.showStatisticts))
                }
            }
        }
    )
    { innerPadding -> Column( modifier = Modifier.padding(innerPadding))
        {
            BackpackItemsList(itemList = itemsUiState.itemList,
                              changeEnable = {coroutineScope.launch { backpackViewModel.remove(it) }},
                              search = backpackViewModel.searchUiState.search.lowercase(Locale.getDefault())
                            )
        }
    }

    InfoSheet(show = show, onShowChange = { show = false },
                typeCounts = weightsUiState.typeWeightList,
                itemCount = allItemsWeight)

    GetTextDialog(title = stringResource(id = R.string.createGroup),
                  textBoxTitle =stringResource(R.string.groupName), openDialog = showGroupAdd, onShowChange = { showGroupAdd = false }) {
        coroutineScope.launch { backpackViewModel.createNewGroup(it) }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoSheet(
    show: Boolean,
    onShowChange: () -> Unit,
    typeCounts: List<WeightType>,
    itemCount: Int
) {
    if (show) {
        val sheetState = rememberModalBottomSheetState()
        val scrollState = rememberScrollState()

        ModalBottomSheet(
            onDismissRequest = {
                onShowChange()
            },
            sheetState = sheetState
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 0.dp, 0.dp, 45.dp)
                .verticalScroll(scrollState)) {
                typeCounts.forEach {
                    TypeLine(weight = it.totalWeight, type = it.type, percentage = (it.totalWeight.toDouble() / itemCount))
                }
            }
        }
    }
}


@Composable
fun TypeLine(
    weight: Int,
    type: String,
    percentage : Double
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp, 0.dp)) {
            
            if (type == "Other") {
                Text(text = stringResource(id = R.string.typeOther), modifier = Modifier.width(75.dp), textAlign = TextAlign.Left)
            } else {
                Text(text = type, modifier = Modifier.width(75.dp), textAlign = TextAlign.Left)
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(text = weight.toString() + "g", modifier = Modifier.width(75.dp), textAlign = TextAlign.Right)
            if (percentage < 0.1) {
                Text(text = "< 1%", modifier = Modifier
                    .width(55.dp)
                    .padding(5.dp, 0.dp), textAlign = TextAlign.Right)
            } else {
                Text(text = (percentage*100).roundToInt().toString() + "%", modifier = Modifier
                    .width(55.dp)
                    .padding(5.dp, 0.dp), textAlign = TextAlign.Right)
            }
        }

        LinearProgressIndicator(
            progress = { percentage.toFloat() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )
    }


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
            Text(text = stringResource(id = R.string.backpackEmpty), textAlign = TextAlign.Center, fontSize = 30.sp)
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
                Icon(imageVector = Icons.Default.Clear, contentDescription = stringResource(id = R.string.buttonAdd) , modifier =  Modifier.padding(20.dp, 5.dp))
            }
        }
    }
}
