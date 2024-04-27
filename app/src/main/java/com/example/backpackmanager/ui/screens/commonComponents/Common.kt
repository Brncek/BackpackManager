package com.example.backpackmanager.ui.screens.commonComponents


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.backpackmanager.R
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
                    modifier = Modifier.height(55.dp)
                )
        },

        actions = {
            IconButton(onClick = {setingsButtonAction()}) {
                Icon(imageVector = Icons.Outlined.Settings, contentDescription = "")
            }
        },

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
                    onClick = { if (index == 0) {
                                    firstTabAction()
                                } else if (index == 1) {
                                    secondTabAction()
                                } else {
                                    thirdTabAction()
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
                name: String,
                description: String,
                descriptionName: String,
                painter: Painter,
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
                Text(text = name, color = MaterialTheme.colorScheme.inversePrimary, fontSize = 25.sp)
                Image(painter = painter, contentDescription = name,
                    Modifier
                        .padding(10.dp)
                        .size(260.dp, 195.dp))
                OutlinedTextField(  value = description,
                            onValueChange = {},    
                            readOnly = true, 
                            label = { Text( text = descriptionName,
                                            color = MaterialTheme.colorScheme.inversePrimary
                                    )},
                            enabled = false,
                            modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 35.dp ).height(125.dp)
                )
                Column( modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 60.dp ),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                    content()
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
        TopBar(searchValue = "", searchValueOnChange = {}, setingsButtonAction = {})
    }
}