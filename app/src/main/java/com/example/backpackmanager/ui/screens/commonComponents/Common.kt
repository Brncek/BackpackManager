package com.example.backpackmanager.ui.screens.commonComponents


import android.net.Uri
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import com.example.backpackmanager.R
import com.example.backpackmanager.database.Item

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    searchValue: String,
    setingsButtonAction: () -> Unit,
    searchValueOnChange: (String) -> Unit,
    leadingButton: @Composable () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            ),
        title = {
                OutlinedTextField(
                    value = searchValue, onValueChange = {searchValueOnChange(it)},
                    label = { Text(text = stringResource(id = R.string.search) )},
                    trailingIcon = {
                        Icon(imageVector = Icons.Rounded.Search, contentDescription = "Search")
                    },
                    textStyle = TextStyle(),
                    maxLines = 1,
                    singleLine = true,
                    modifier = Modifier.widthIn(0.dp,270.dp)
                )
        },

        actions = {
            IconButton(onClick = {setingsButtonAction()}, 
                        modifier = Modifier.fillMaxHeight()) {
                Icon(imageVector = Icons.Outlined.Settings, contentDescription = "")
            }
        },
        modifier = Modifier.height(70.dp),
        navigationIcon = {
            leadingButton()
        }
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
    if (show) {
        val sheetState = rememberModalBottomSheetState()
        val scrollState = rememberScrollState()

        ModalBottomSheet(
            onDismissRequest = {
                onChangeShow(false)
            },
            sheetState = sheetState
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()
                                                                        .verticalScroll(scrollState)) {
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
                    label = { Text( text = stringResource(id = R.string.typeText),
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


@Composable
fun ItemCard(item: Item, modifier: Modifier, showAdded: Boolean) {
    Card( modifier = modifier, elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)) {
        Row (verticalAlignment = Alignment.CenterVertically,
             modifier = Modifier
                 .height(70.dp)
                 .fillMaxWidth())
        {

            Text(text = item.name, textAlign = TextAlign.Left, modifier = Modifier.padding(15.dp, 0.dp,0.dp, 0.dp).width(70.dp))

            Spacer(modifier = Modifier.weight(1f))

            if (showAdded && item.addedToBackpack > 0) {
                FilterChip(
                    onClick = {},
                    label = { Text(stringResource(id = R.string.added)) },
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
            } else if (!showAdded) {
                FilterChip(
                    onClick = {},
                    label = { Text(item.addedToBackpack.toString() + " " + stringResource(id = R.string.peacesShort) ) },
                    selected = false,
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


@Composable
fun DeleteDialog(
    openDialog: Boolean,
    onShowChange: () ->  Unit,
    confirmAction: () -> Unit,
) {
    if (openDialog) {

        AlertDialog (
            title = {
                Text(text = stringResource(id = R.string.deleteMessage), textAlign = TextAlign.Center)
            },

            onDismissRequest = {
                onShowChange()
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        confirmAction()
                        onShowChange()
                    }
                ) {
                    Text(stringResource(id = R.string.confirm))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onShowChange()
                    },
                ) {
                    Text(stringResource(id = R.string.dismiss))
                }
            },
            text = {
            },
            icon = {
                Icon(imageVector = Icons.Default.Warning, contentDescription = "")
            }
        )
    }
}

@Composable
fun AddDialog(
    titleText: String,
    openDialog: Boolean,
    onShowChange: () ->  Unit,
    confirmAction: (Int) -> Unit,
) {
    if (openDialog) {
        var dialogString by remember { mutableStateOf("") }

        AlertDialog (
            title = {
                Text(text = titleText)
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
                    Text(stringResource(id = R.string.confirm))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onShowChange()
                    }
                ) {
                    Text(stringResource(id = R.string.dismiss))
                }
            },
            text = {
                OutlinedTextField(
                    value = dialogString,
                    onValueChange = {dialogString = it},
                    label = { Text(text = stringResource(id = R.string.amount) ) },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    textStyle = TextStyle(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    maxLines = 1,
                    singleLine = true
                )
            }
        )
    }
}


@Composable
fun GetTextDialog(
    title: String,
    textBoxTitle: String,
    openDialog: Boolean,
    onShowChange: () ->  Unit,
    confirmAction: (String) -> Unit,
) {
    if (openDialog) {
        var dialogString by remember { mutableStateOf("") }

        AlertDialog (
            title = {
                Text(text = title)
            },

            onDismissRequest = {
                onShowChange()
            },
            confirmButton = {
                TextButton(
                    enabled = dialogString.isNotBlank(),
                    onClick = {
                        onShowChange()
                        confirmAction(dialogString)
                    }
                ) {
                    Text(stringResource(id = R.string.confirm))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onShowChange()
                    }
                ) {
                    Text(stringResource(id = R.string.dismiss))
                }
            },
            text = {
                OutlinedTextField(
                    value = dialogString,
                    onValueChange = {dialogString = it},
                    label = { Text(text = textBoxTitle ) },
                    textStyle = TextStyle(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    maxLines = 1,
                    singleLine = true
                )
            }
        )
    }
}


fun validateAddNumber(numberString:String) : Boolean {
    try {
        numberString.toInt()
    } catch (e : Exception) {
        return false
    }
    return numberString.isNotBlank() && numberString.toInt() > 0
}
