package com.example.backpackmanager.ui.screens.editingScreen

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import com.example.backpackmanager.database.DataRepository
import com.example.backpackmanager.database.Item
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

class EditingScreenViewModel(private val dataRepository: DataRepository) : ViewModel() {
    var editedItem by mutableStateOf(ItemHolder())
        private set

    var itemUiState by mutableStateOf(ItemUiState())
        private set

    fun change(itemDetails: ItemDetails) {
        itemUiState = ItemUiState(itemDetails, valid(itemDetails))
    }

    fun setEdited(item : Item) {
        val itemDetails = ItemDetails(item.id, item.name, item.type, item.weight.toString(), item.picturePath, item.selected)
        editedItem = ItemHolder(item)
        itemUiState = ItemUiState(itemDetails, true)
    }

    private fun valid(itemDetails: ItemDetails): Boolean {

        var parseTest = false
        try {
            itemDetails.weight.toInt()
            parseTest = true
        } catch (e: Exception) {}

        return itemDetails.name.isNotBlank() && itemDetails.picturePath.isNotBlank() && itemDetails.picturePath != "null"
                && itemDetails.weight.isNotBlank() && parseTest
    }

    suspend fun saveItem(context: Context) {
        if (itemUiState.valid) {
            var newFile : Uri? = context.copyFileToAppStorage(itemUiState.itemDetails.picturePath.toUri())
            if (newFile != null) {
                change(itemUiState.itemDetails.copy(picturePath = newFile.toString()))
                dataRepository.insert(itemUiState.itemDetails.toItem())
            }
        }
    }


}

data class ItemUiState(
    val itemDetails: ItemDetails = ItemDetails(),
    val valid: Boolean = false
)

data class ItemHolder(
    val item : Item? = null
)

data class ItemDetails(
    val id: Int = 0,
    val name: String = "",
    val type: String = "Other",
    val weight: String = "",
    val picturePath: String = "",
    val selected: String = "F"
)

fun ItemDetails.toItem(): Item = Item(
    id = id,
    name = name,
    type = type,
    weight = weight.toInt(),
    picturePath = picturePath,
    selected = selected
)

fun Context.copyFileToAppStorage(fileUri: Uri): Uri? {
    val contentResolver = contentResolver
    val inputStream: InputStream? = contentResolver.openInputStream(fileUri)

    // Get the file name
    var fileName: String? = null
    contentResolver.query(fileUri, null, null, null, null)?.use { cursor ->
        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (cursor.moveToFirst() && nameIndex != -1) {
            fileName = cursor.getString(nameIndex)
        }
    }

    if (fileName == null) return null;


    val outputFile = File(filesDir, fileName)
    val outputStream: OutputStream = FileOutputStream(outputFile)

    return try {
        if (inputStream != null) {

            val buffer = ByteArray(1024)
            var bytesRead: Int
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }
            inputStream.close()
        }
        outputStream.close()
        outputFile.toUri()
    } catch (e: Exception) {
        null
    }
}




