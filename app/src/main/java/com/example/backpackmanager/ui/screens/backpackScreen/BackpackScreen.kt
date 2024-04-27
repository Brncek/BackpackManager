package com.example.backpackmanager.ui.screens.backpackScreen

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
import com.example.backpackmanager.R
import com.example.backpackmanager.ui.screens.commonComponents.BottomBar
import com.example.backpackmanager.ui.screens.commonComponents.DetailSheet
import com.example.backpackmanager.ui.screens.commonComponents.TopBar
import com.example.backpackmanager.ui.theme.BackpackManagerTheme


    @Composable
    fun BackpackScreen() {

        var selectedItem by remember { mutableIntStateOf(1) } //TODO:: remove to viewmodel
        var searchVal by remember { mutableStateOf("") }

        Scaffold (  topBar = { TopBar(searchValue = searchVal, searchValueOnChange = {searchVal = it}, setingsButtonAction = {})} ,
                    bottomBar = { BottomBar(selectedTab = selectedItem, tabOnclick = {selectedItem = it} )}
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



    @Composable
    fun Inner() {
        var show by remember { mutableStateOf(true) }

        //TODO::
        DetailSheet(
            show = show,
            name = "TEST",
            description = "TEST",
            descriptionName = "Test",
            painter =  painterResource(id = R.drawable.testimage),
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
