package com.example.backpackmanager.ui.screens.itemScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.backpackmanager.R
import com.example.backpackmanager.ui.ViewModelCreator
import com.example.backpackmanager.ui.navigation.ScreenDest
import com.example.backpackmanager.ui.screens.commonComponents.DetailSheet
import com.example.backpackmanager.ui.screens.commonComponents.TopBar
import com.example.backpackmanager.ui.theme.BackpackManagerTheme
import kotlin.random.Random

object ItemsScreenDestination : ScreenDest {
    override val route = "itemsScreen"
}

@Composable
fun ItemScreen(
        setingsButtonAction: () -> Unit,
        viewModel: ItemsViewModel = viewModel(factory = ViewModelCreator.Factory)
    ) {
    var searchVal by remember { mutableStateOf("") } //TODO: View model move
    Column {
        TopBar(searchValue = searchVal, searchValueOnChange = {searchVal = it}, setingsButtonAction = {setingsButtonAction() })
        Inner()
    }
}


@Composable
fun Inner() {
    var show by remember { mutableStateOf(false) } //TODO: View model move
    var imageID by remember { mutableIntStateOf(0) } //TODO: View model move

    if (Random.nextBoolean()) {
        imageID = R.drawable.noitemimage1
    } else {
        imageID = R.drawable.noitemimage2
    }

    Button(onClick = {show = true}) {
        Text(text = "Show bottom")
    }
    
    //TODO::
    DetailSheet(
        show = show,
        name = "TEST",
        description = "TEST",
        descriptionName = "Test",
        painter =  painterResource(id = imageID),
        onChangeShow = {show = it}
    ) {
        Row {
            Button(onClick = {} ) {
                Text(text = "TEST1")
            }

            Button(onClick = {}, modifier = Modifier.padding(10.dp, 0.dp)) {
                Text(text = "TEST2")
            }

            Button(onClick = {} ) {
                Text(text = "TEST3")
            }
        }
    }
}


@Preview(showBackground = true)
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun Preview() {
    BackpackManagerTheme {
    }
}