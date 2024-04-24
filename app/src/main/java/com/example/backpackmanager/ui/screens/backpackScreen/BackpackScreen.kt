package com.example.backpackmanager.ui.screens.backpackScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.backpackmanager.R
import com.example.backpackmanager.ui.theme.BackpackManagerTheme


    @Composable
    fun BackpackScreen() {
        Scaffold (  topBar = { Top() } ,
                    bottomBar = { Bottom()}
        )
        { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding),
            ) {
                Inner()
            }

        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Top() {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,

            ),
            title = {
                TextField(value = "", onValueChange = {}, //TODO:onchange
                    label = { Text(text = stringResource(id = R.string.search))},

                    trailingIcon = {
                        Icon(imageVector = Icons.Rounded.Search, contentDescription = "Search")
                    },
                    textStyle = TextStyle(),
                    modifier = Modifier.padding(0.dp, 25.dp)
                )
            },

            actions = {
                IconButton(onClick = {}) { //TODO:
                    Icon(imageVector = Icons.Outlined.Settings, contentDescription = "")
                }
            }
        )
    }

    @Composable
    fun Bottom() {
        BottomAppBar() {
            var selectedItem by remember { mutableIntStateOf(0) } //TODO:: remove to viewodel
            val items = listOf(stringResource(id = R.string.items),
                                stringResource(id = R.string.backpack),
                                stringResource(id = R.string.groups))

            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(Icons.Outlined.ArrowDropDown, contentDescription = item) },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index },
                        //TODO::colors
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Inner() {
        //TODO::

        val sheetState = rememberModalBottomSheetState()
        var showBottomSheet by remember { mutableStateOf(true) }
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                Column(Modifier.padding(50.dp)) {
                    Text(text = "Hide bottom sheet")

                    Button(onClick = {}) {
                        Text(text = "Hide bottom sheet")
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
            Top()
        }
    }
