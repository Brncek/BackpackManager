package com.example.backpackmanager.ui.screens.commonComponents


import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter
import com.example.backpackmanager.R
import com.example.backpackmanager.database.Item
import com.example.backpackmanager.ui.theme.BackpackManagerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    searchValue: String,
    setingsButtonAction: () -> Unit,
    searchValueOnChange: (String) -> Unit
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            ),
        title = {
                OutlinedTextField(
                    value = searchValue, onValueChange = {searchValueOnChange(it)}, //TODO:text align
                    label = { Text(text = stringResource(id = R.string.search) )},
                    trailingIcon = {
                        Icon(imageVector = Icons.Rounded.Search, contentDescription = "Search")
                    },
                    textStyle = TextStyle(),
                )
        },

        actions = {
            IconButton(onClick = {setingsButtonAction()}, 
                        modifier = Modifier.fillMaxHeight()) {
                Icon(imageVector = Icons.Outlined.Settings, contentDescription = "")
            }
        },
        modifier = Modifier.height(70.dp)
    )
}

@Composable
fun BottomBar(
    selectedTab: Int,
    firstTabAction: () -> Unit,
    secondTabAction: () -> Unit,
    thirdTabAction: () -> Unit,
) {
    val items = listOf( stringResource(id = R.string.items),
        stringResource(id = R.string.backpack),
        stringResource(id = R.string.groups))

    BottomAppBar {
        NavigationBar {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.ArrowDropDown, contentDescription = item) },
                    label = { Text(item) },
                    selected = selectedTab == index,
                    onClick = {
                        when (index) {
                            0 -> {
                                firstTabAction()
                            }
                            1 -> {
                                secondTabAction()
                            }
                            else -> {
                                thirdTabAction()
                            }
                        }
                              },
                    //TODO::colors
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailSheet(show: Boolean,
                item :Item,
                onChangeShow: (Boolean) -> Unit,
                content: @Composable ColumnScope.() -> Unit,
) {

    val sheetState = rememberModalBottomSheetState()

    if (show) {
        ModalBottomSheet(
            onDismissRequest = {
                onChangeShow(false)
            },
            sheetState = sheetState
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Text(text = item.name, color = MaterialTheme.colorScheme.inversePrimary, fontSize = 25.sp)
                Image(painter = rememberAsyncImagePainter(  model = item.picturePath.toUri(),
                                                            error = painterResource(id = R.drawable.noimage),
                                                            fallback = painterResource(id = R.drawable.noimage))
                    , contentDescription = item.name,
                    Modifier
                        .padding(5.dp)
                        .size(260.dp, 195.dp))
                OutlinedTextField(  value = item.weight.toString() + "g",
                            onValueChange = {},    
                            readOnly = true, 
                            label = { Text( text = stringResource(id = R.string.itemWeight),
                                            color = MaterialTheme.colorScheme.inversePrimary
                                    )},
                            enabled = false,
                            modifier = Modifier
                                .padding(10.dp, 5.dp, 10.dp, 5.dp)
                )

                OutlinedTextField(  value = item.type,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text( text = stringResource(id = R.string.TypeText),
                        color = MaterialTheme.colorScheme.inversePrimary
                    )},
                    enabled = false,
                    modifier = Modifier
                        .padding(10.dp, 5.dp, 10.dp, 35.dp)
                )
                
                Column( modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 60.dp ),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                    content()
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemCard(item: Item, modifier: Modifier, showAdded: Boolean) {
    Card( modifier = modifier, elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)) {
        Row (verticalAlignment = Alignment.CenterVertically,
             modifier = Modifier
                 .height(70.dp)
                 .fillMaxWidth())
        {

            Text(text = item.name, textAlign = TextAlign.Left, modifier = Modifier.padding(15.dp, 0.dp))

            Spacer(modifier = Modifier.weight(1f))

            if (showAdded && item.selected == "T") {
                FilterChip(
                    onClick = {},
                    label = { Text(stringResource(id = R.string.Added)) },
                    selected = false,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    },
                    modifier = Modifier.padding(15.dp,0.dp,0.dp,0.dp)
                )
            }


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



@Preview(showBackground = true)
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun Preview() {
    BackpackManagerTheme {
        //TopBar(searchValue = "", searchValueOnChange = {}, setingsButtonAction = {})
        val item = Item(0,  "TEST", "D", 50, "","T")
        Column {
            ItemCard(item = item, modifier = Modifier.padding(10.dp), showAdded = true)
        }

    }
}

