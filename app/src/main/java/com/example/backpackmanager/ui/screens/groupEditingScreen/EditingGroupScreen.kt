package com.example.backpackmanager.ui.screens.groupEditingScreen

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.backpackmanager.R
import com.example.backpackmanager.database.Item
import com.example.backpackmanager.database.ItemGroupAmount
import com.example.backpackmanager.ui.ViewModelCreator
import com.example.backpackmanager.ui.navigation.GroupNameMover
import com.example.backpackmanager.ui.navigation.ScreenDest
import com.example.backpackmanager.ui.screens.commonComponents.AddDialog
import com.example.backpackmanager.ui.screens.commonComponents.DeleteDialog
import com.example.backpackmanager.ui.screens.commonComponents.DetailSheet
import com.example.backpackmanager.ui.screens.commonComponents.GetTextDialog
import kotlinx.coroutines.launch

object EditingGroupScreenDestination : ScreenDest {
    override val route = "editingGroupScreen"
}

@Composable
fun EditingGroupScreen (
    viewModel: EditingGroupViewModel = viewModel(factory = ViewModelCreator.Factory),
    navigateBack: () -> Unit,
    navigateToBackpack: () -> Unit
) {
    val items by viewModel.itemsGroupUiState.collectAsState()
    val itemAmounts by viewModel.itemsGroupsAmountsUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var showDetail by remember { mutableStateOf(false)}
    var showEditAmount by remember { mutableStateOf(false)}
    var showDeleteDialog by remember { mutableStateOf(false)}
    var showDeleteDialogGroup by remember { mutableStateOf(false)}
    var changeNameDialog by remember { mutableStateOf(false)}
    var detailItem by remember { mutableStateOf(Item())}

    Column (modifier = Modifier
        .fillMaxSize()
        .padding(10.dp)) {

        Text(text = GroupNameMover.groupName, modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 10.dp, 0.dp, 10.dp), fontSize = 25.sp,
            textAlign = TextAlign.Center)

        Button(onClick = { coroutineScope.launch { viewModel.addToBackpack()}
                            navigateToBackpack()
                         }, modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)) {
            Text(text = stringResource(id = R.string.addToBackpack))
        }

        Button(onClick = { coroutineScope.launch { viewModel.replaceBackpack()}
                           navigateToBackpack()
                         } , modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)) {
            Text(text = stringResource(id = R.string.replaceBackpack))
        }

        Row (modifier = Modifier.fillMaxWidth()){
            Button(onClick = { showDeleteDialogGroup = true }, modifier = Modifier
                .weight(0.5f)
                .fillMaxWidth()
                .padding(10.dp)) {
                Icon(imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(id = R.string.groupDeleteButton))
            }


            Button(onClick = { changeNameDialog = true }, modifier = Modifier
                .weight(0.5f)
                .fillMaxWidth()
                .padding(10.dp)) {
                Icon(imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(id = R.string.editNameGroupButton))
            }
        }


        HorizontalDivider(thickness = 3.dp, modifier = Modifier.padding(10.dp))

        Text(text = stringResource(R.string.groupItems), modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 5.dp, 0.dp, 5.dp), fontSize = 20.sp,
            textAlign = TextAlign.Center)


        GroupItemList(itemsList = items.itemList, itemWeights = itemAmounts.itemAmounts) {
            detailItem = it
            showDetail = true
        }

        DetailSheet(show = showDetail, item = detailItem, onChangeShow = {showDetail = false} ) {
            Row (modifier = Modifier.fillMaxWidth()){
                Button(onClick = {  showDetail = false; showDeleteDialog = true },
                    modifier = Modifier
                        .weight(0.5f)
                        .fillMaxWidth()
                        .padding(10.dp)) {
                    Icon(imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(id = R.string.groupDeleteButton))
                }

                Button(onClick = { showDetail = false; showEditAmount = true }, modifier = Modifier
                    .weight(0.5f)
                    .fillMaxWidth()
                    .padding(10.dp)) {
                    Icon(imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(id = R.string.editNameGroupButton))
                }
            }
        }

        GetTextDialog(title = stringResource(id = R.string.changeGroupName),
            textBoxTitle =stringResource(id = R.string.groupName) , openDialog = changeNameDialog,
            onShowChange = { changeNameDialog = false }) {
            navigateBack()
            coroutineScope.launch {
                viewModel.changeGroupName(it)
            }
        }

        DeleteDialog(openDialog = showDeleteDialog, onShowChange = { showDeleteDialog = false }) {
            coroutineScope.launch { viewModel.deleteItemFromGroup(detailItem.id) }
        }

        DeleteDialog(openDialog = showDeleteDialogGroup, onShowChange = { showDeleteDialogGroup = false }) {
            coroutineScope.launch { viewModel.deleteGroup() }
            navigateBack()
        }

        AddDialog(openDialog = showEditAmount, onShowChange = { showEditAmount = false }, confirmAction = {
            coroutineScope.launch {viewModel.changeItemAmount(detailItem.id, it)}
        }, titleText = stringResource(id = R.string.changeAmount))
    }
}

@Composable
fun GroupItemList(
    itemsList :List<Item>,
    itemWeights :List<ItemGroupAmount>,
    onClick: (Item) -> Unit = {}
) {
    val itemWeightsMap : MutableMap<Int, Int>  = mutableMapOf()

    itemWeights.forEach{
        itemWeightsMap[it.id] = it.amount
    }

    LazyColumn {
        items(items = itemsList, key = { it.id }) {
                item -> GroupItemCard(item = item, amount = itemWeightsMap[item.id] ?: 1,
            modifier = Modifier
                .padding(10.dp)
                .clickable {
                    onClick(item)
                })
        }
    }
}


@Composable
fun GroupItemCard(item: Item, amount:Int, modifier: Modifier) {
    Card( modifier = modifier, elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)) {
        Row (verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(70.dp)
                .fillMaxWidth())
        {

            Text(text = item.name, textAlign = TextAlign.Left,
                modifier = Modifier.padding(15.dp, 0.dp,0.dp, 0.dp).width(70.dp))

            Spacer(modifier = Modifier.weight(1f))


            FilterChip(
                onClick = {},
                label = { Text(amount.toString() + " " + stringResource(id = R.string.peacesShort) ) },
                selected = false,
                modifier = Modifier.padding(15.dp,0.dp,0.dp,0.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = rememberAsyncImagePainter(model = Uri.parse(item.picturePath),
                    error = painterResource(id = R.drawable.noimage),
                    fallback = painterResource(id = R.drawable.noimage)
                ),
                contentDescription = item.name,
                modifier = Modifier
                    .height(70.dp)
                    .width(90.dp)
            )
        }
    }
}