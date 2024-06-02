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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.backpackmanager.database.Type
import com.example.backpackmanager.ui.ViewModelCreator
import com.example.backpackmanager.ui.navigation.ScreenDest
import com.example.backpackmanager.ui.screens.commonComponents.DeleteDialog
import com.example.backpackmanager.ui.screens.commonComponents.GetTextDialog
import kotlinx.coroutines.launch


object SetingsScreenDestination : ScreenDest {
    override val route = "setingsScreen"
}

@Composable
fun SetingsScreen(typesViewModel : TypesViewModel = viewModel(factory = ViewModelCreator.Factory)) {

    val coroutineScope = rememberCoroutineScope()
    var openAddDialog by remember { mutableStateOf(false) }
    var openDeleteDialog by remember { mutableStateOf(false) }
    var deletedType by remember { mutableStateOf(Type()) }
    val typesUiState by typesViewModel.typeUiState.collectAsState()

    Column {
        Text(text = stringResource(R.string.Types), modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 25.dp, 0.dp, 5.dp), fontSize = 30.sp,
            textAlign = TextAlign.Center)


        Button(onClick = { openAddDialog = true
                         }, modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)) {
            Text(text = stringResource(id = R.string.AddTypeButton))
        }

        HorizontalDivider(thickness = 3.dp, modifier = Modifier.padding(10.dp))

        LazyColumn {
            items(items = typesUiState.typeList, key = { it.typeName }) {
                    type -> TypeCard(type = type, delete = { deletedType = it; openDeleteDialog = true })
            }
        }
    }


    GetTextDialog(title = stringResource(id = R.string.AddTypeButton),
        textBoxTitle =stringResource(id = R.string.itemName) , openDialog = openAddDialog,
        onShowChange = { openAddDialog = false }) {

        coroutineScope.launch{typesViewModel.addType(it)}
    }

    DeleteDialog(openDialog = openDeleteDialog, onShowChange = { openDeleteDialog = false }) {
        coroutineScope.launch { typesViewModel.removeType(deletedType) }
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
