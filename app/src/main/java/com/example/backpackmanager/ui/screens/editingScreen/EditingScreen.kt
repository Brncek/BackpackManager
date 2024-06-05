package com.example.backpackmanager.ui.screens.editingScreen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.backpackmanager.R
import com.example.backpackmanager.database.Type
import com.example.backpackmanager.ui.ViewModelCreator
import com.example.backpackmanager.ui.navigation.ScreenDest
import kotlinx.coroutines.launch


object ItemEditScreenDestination : ScreenDest {
    override val route = "itemEditScreen"
}

@Composable
fun EditingScreen (
    editingScreenViewModel: EditingScreenViewModel = viewModel(factory = ViewModelCreator.Factory),
    onLeave: () -> Unit = {}
) {

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val types by editingScreenViewModel.typeUiState.collectAsState()

    val scrollState = rememberScrollState()

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        uri: Uri? -> editingScreenViewModel.change(editingScreenViewModel.itemUiState.itemDetails.copy(picturePath = uri.toString()))
    }

    var show by remember { mutableStateOf(false) }

    Scaffold(
      bottomBar = {
          Button(onClick = {coroutineScope.launch {editingScreenViewModel.saveItem(context) }
                            onLeave()
                           }, modifier = Modifier
              .fillMaxWidth()
              .padding(10.dp),
              enabled = editingScreenViewModel.itemUiState.valid) {
              Text(text = stringResource(id = R.string.SaveItem))
          }
      }
    ) { innerPadding -> Column (modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .verticalScroll(scrollState)) {

        Text(text = stringResource(R.string.editorTitle), modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 25.dp, 0.dp, 5.dp), fontSize = 30.sp,
            textAlign = TextAlign.Center)

        TypableItems(itemDetails = editingScreenViewModel.itemUiState.itemDetails, onValueChange = {editingScreenViewModel.change(it)})
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(id = R.string.typeText), modifier = Modifier
                .padding(15.dp, 5.dp, 0.dp, 5.dp), fontSize = 30.sp)

            if (editingScreenViewModel.itemUiState.itemDetails.type == "Other") {
                Text(text = stringResource(id = R.string.typeOther), modifier = Modifier
                    .padding(10.dp, 5.dp), fontSize = 30.sp)
            } else {
                Text(text = editingScreenViewModel.itemUiState.itemDetails.type, modifier = Modifier
                    .padding(10.dp, 5.dp), fontSize = 30.sp)
            }

        }

        Button(onClick = {show = true}, modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)) {
            Text(text = stringResource(id = R.string.changeType))
        }

        Button(onClick = { galleryLauncher.launch("image/*") }, modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)) {
            Text(text = stringResource(id = R.string.loadImage))
        }

        HorizontalDivider(thickness = 3.dp, modifier = Modifier.padding(10.dp))

        Image(
            painter = rememberAsyncImagePainter(model = editingScreenViewModel.itemUiState.itemDetails.picturePath.toUri(),
                                                error = painterResource(id = R.drawable.noimage),
                                                fallback = painterResource(id = R.drawable.noimage)),
            contentDescription = editingScreenViewModel.itemUiState.itemDetails.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        TypeSelectSheet(show = show, itemDetails = editingScreenViewModel.itemUiState.itemDetails
            ,onValueChange = {editingScreenViewModel.change(it)}, onChangeShow = {show = it}, types = types.typeList)
        }
    }
}

@Composable
fun TypableItems(
    itemDetails: ItemDetails,
    onValueChange: (ItemDetails) -> Unit
) {
    Column {
        OutlinedTextField(
            value = itemDetails.name,
            onValueChange = {onValueChange(itemDetails.copy(name = it))},
            label = { Text(text = stringResource(id = R.string.itemName) ) },
            textStyle = TextStyle(),
            maxLines = 1,
            singleLine = true ,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )

        OutlinedTextField (
            value = itemDetails.weight,
            onValueChange = {onValueChange(itemDetails.copy(weight = it))},
            label = { Text(text = stringResource(id = R.string.itemWeight) ) },
            textStyle = TextStyle(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            maxLines = 1,
            singleLine = true ,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypeSelectSheet(
    show: Boolean,
    onChangeShow: (Boolean) -> Unit,
    itemDetails: ItemDetails,
    onValueChange: (ItemDetails) -> Unit,
    types: List<Type>
) {
    if (show) {
        val sheetState = rememberModalBottomSheetState()

        ModalBottomSheet(
            onDismissRequest = {
                onChangeShow(false)
            },
            sheetState = sheetState
        ) {
            Text(text = stringResource(id = R.string.selectType), modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp, 5.dp), fontSize = 30.sp)
            TypeButtons(itemDetails = itemDetails, onValueChange = {onValueChange(it)}, types)
            Spacer(modifier = Modifier.padding(0.dp, 40.dp))
        }
    }
}

@Composable
fun TypeButtons(
    itemDetails: ItemDetails,
    onValueChange: (ItemDetails) -> Unit,
    types: List<Type>
) {
    Column (modifier = Modifier.height(270.dp)) {

        TypeSelectButton(type = "Other", name = stringResource(id = R.string.typeOther), actual = itemDetails.type,
            onClick = {onValueChange(itemDetails.copy(type = "Other"))})


        Text(text = stringResource(id = R.string.userTypes), modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp, 5.dp), fontSize = 20.sp)

        HorizontalDivider(thickness = 3.dp, modifier = Modifier.padding(10.dp))

        LazyColumn {
            items(items = types, key = { it.typeName }) {
                    type -> TypeSelectButton(type = type.typeName, name = type.typeName, actual = itemDetails.type,
                        onClick = {onValueChange(itemDetails.copy(type = type.typeName))})
            }
        }

        HorizontalDivider(thickness = 3.dp, modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 35.dp))
    }
}

@Composable
fun TypeSelectButton(
    type: String,
    name: String,
    actual: String,
    onClick: (String) -> Unit
) {
    Row (modifier = Modifier.fillMaxWidth())
    {
        RadioButton(
            selected = actual == type ,
            onClick = { onClick(type) },
            modifier = Modifier.padding(10.dp, 2.dp)
        )

        Text(text = name, modifier = Modifier.align(Alignment.CenterVertically))
    }
}

