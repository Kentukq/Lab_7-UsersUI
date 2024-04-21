package com.topic2.android.notes.ui.componets.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.AlertDialog
import androidx.compose.material.BottomDrawer
import androidx.compose.material.BottomDrawerState
import androidx.compose.material.BottomDrawerValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberBottomDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.topic2.android.notes.R
import com.topic2.android.notes.domain.model.ColorModel
import com.topic2.android.notes.domain.model.NEW_NOTE_ID
import com.topic2.android.notes.domain.model.NoteModel
import com.topic2.android.notes.routing.NotesRouter
import com.topic2.android.notes.routing.Screen
import com.topic2.android.notes.ui.componets.NoteColor
import com.topic2.android.notes.util.fromHex
import com.topic2.android.notes.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@Composable
private fun ColorPicker(
    colors: List<ColorModel>,
    onColorSelect: (ColorModel) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            text = "Color picker",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(colors.size){
                    itemIndex ->
                val color = colors[itemIndex]
                ColorItem(
                    color = color,
                    onColorSelect = onColorSelect
                )
            }
        }
    }
}

@Preview @Composable
fun ColorPickerPreview() {
    ColorPicker(
        colors = listOf(
            ColorModel.DEFAULT,
            ColorModel.DEFAULT,
            ColorModel.DEFAULT
        )
    ) { }
}


@Composable
fun ColorItem(
    color: ColorModel,
    onColorSelect: (ColorModel) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = { onColorSelect(color) }
            )
    ) {
        NoteColor(
            modifier = Modifier
                .padding(10.dp),
            color = Color.fromHex(color.hex),
            size = 80.dp,
            border = 2.dp
        )
        Text(
            text = color.name,
            fontSize = 22.sp,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .align(Alignment.CenterVertically)
        )
    }
}

@Preview @Composable
fun ColorItemPreview() { ColorItem(ColorModel.DEFAULT) {} }
fun SaveNoteScreen(viewModel: MainViewModel) {
@Composable
private fun SaveNoteTopAppBar(
    isEditingMode: Boolean,
    onBackClick: () -> Unit,
    onSaveNoteClick: () -> Unit,
    onOpenColorPickerClick: () -> Unit,
    onDeleteNoteClick: ()  -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Save Note",
                color = MaterialTheme.colors.onPrimary
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Save Note Button",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        },
        actions = {
            IconButton(onClick = onSaveNoteClick) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save Note",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
            IconButton(onClick = onOpenColorPickerClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_color_lens_24),
                    contentDescription = "Open Color Picker Button",
                    tint = MaterialTheme.colors.onPrimary
                )
            }