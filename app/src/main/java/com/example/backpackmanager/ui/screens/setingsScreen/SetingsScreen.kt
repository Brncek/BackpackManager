package com.example.backpackmanager.ui.screens.setingsScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.backpackmanager.R
import com.example.backpackmanager.database.Type
import com.example.backpackmanager.ui.ViewModelCreator
import com.example.backpackmanager.ui.navigation.ScreenDest
import kotlinx.coroutines.launch


object SetingsScreenDestination : ScreenDest {
    override val route = "setingsScreen"
}

@Composable
fun SetingsScreen( setingsViewModel : SetingsViewModel = viewModel(factory = ViewModelCreator.Factory)) {

    val coroutineScope = rememberCoroutineScope()
    var openDialog by remember { mutableStateOf(false) }
    var dialogString by remember { mutableStateOf("") }
    val typesUiState by setingsViewModel.typeUiState.collectAsState()

    Column {
        Text(text = stringResource(R.string.SetingsTitle), modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 25.dp, 0.dp, 5.dp), fontSize = 30.sp,
            textAlign = TextAlign.Center)

        Button(onClick = { /*TODO*/ }, modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)) {
            Text(text = stringResource(id = R.string.ChangeLanguageButton))
        }

        Button(onClick = { /*TODO*/ }, modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)) {
            Text(text = stringResource(id = R.string.ChangeTheameButton))
        }

        Button(onClick = {dialogString = ""
                          openDialog = true
                         }, modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)) {
            Text(text = stringResource(id = R.string.AddTypeButton))
        }

        HorizontalDivider(thickness = 3.dp, modifier = Modifier.padding(10.dp))

        LazyColumn {
            items(items = typesUiState.typeList, key = { it.typeName }) {
                    type -> TypeCard(type = type, delete = {coroutineScope.launch { setingsViewModel.removeType(it) } })
            }
        }
    }


    if (openDialog) {

        AlertDialog (
            title = {
                Text(text = stringResource(id = R.string.AddTypeButton))
            },

            onDismissRequest = {
                openDialog = false
            },
            confirmButton = {
                TextButton(
                    enabled = dialogString.isNotBlank(),
                    onClick = {
                        coroutineScope.launch{setingsViewModel.addType(dialogString)}
                        openDialog = false
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                    }
                ) {
                    Text("Dismiss")
                }
            },
            text = {
                OutlinedTextField(
                    value = dialogString,
                    onValueChange = {dialogString = it},
                    label = { Text(text = stringResource(id = R.string.itemName) ) },
                    textStyle = TextStyle(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
            }
        )
    }
}

@Composable
fun TypeCard(type : Type, delete: (Type) -> Unit = {} ) {
    Card(elevation = CardDefaults.cardElevation(defaultElevation = 5.dp), modifier = Modifier
        .fillMaxWidth()
        .height(65.dp)
        .padding(10.dp)) {
        Row {
            Text(text = type.typeName, textAlign = TextAlign.Left, modifier = Modifier.padding(10.dp))
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { delete(type) }) {
               Icon(imageVector = Icons.Default.Close , contentDescription = stringResource(id = R.string.DeleteButton))
            }
        }
    }

}
