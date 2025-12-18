package com.blacksnowymanx.navigation

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.blacksnowymanx.todoincomposeversion2.R
import com.blacksnowymanx.todoincomposeversion2.roomListNames.ListName
import com.blacksnowymanx.todoincomposeversion2.roomListNames.ListNameViewModel
import kotlin.math.roundToInt

@Composable
fun HomeScreen(
    navController: NavHostController,
    listNameViewModel: ListNameViewModel
) {
    HomeComp(navController, listNameViewModel)
}

@Composable
fun HomeComp(
    navController: NavHostController,
    listNameViewModel: ListNameViewModel
) {
    val dbList by listNameViewModel.allListNames.observeAsState(emptyList())
    val list = remember { mutableStateListOf<ListName>() }

    LaunchedEffect(dbList) {
        list.clear()
        list.addAll(dbList)
    }

    val context = LocalContext.current
    var listNameText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(horizontal = 16.dp)
    ) {

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = listNameText,
                onValueChange = { listNameText = it },
                label = { Text("Enter the name of your List") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(20.dp))

            Button(onClick = {
                if (listNameText.isNotBlank()) {
                    listNameViewModel.insert(
                        ListName(name = listNameText.trim(), description = "")
                    )
                    listNameText = ""
                } else {
                    Toast.makeText(context, "List Name Cannot be Empty", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(
                items = list,
                key = { _, item -> item.id }
            ) { index, item ->
                ReorderableListNameCard(
                    startIndex = index,
                    item = item,
                    list = list,
                    navController = navController,
                    listNameViewModel = listNameViewModel,
                    onTrashClick = {
                        listNameViewModel.delete(item)
                    }
                )
            }
        }
    }
}

@Composable
fun ReorderableListNameCard(
    startIndex: Int,
    item: ListName,
    list: MutableList<ListName>,
    navController: NavHostController,
    listNameViewModel: ListNameViewModel,
    onTrashClick: () -> Unit
) {
    val context = LocalContext.current
    var isEditing by remember { mutableStateOf(false) }
    var editedText by remember(item.name) { mutableStateOf(item.name) }

    var dragOffset by remember { mutableFloatStateOf(0f) }
    var currentIndex by remember { mutableIntStateOf(startIndex) }

    val itemHeightPx = with(LocalDensity.current) { 90.dp.toPx() }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .offset { IntOffset(0, dragOffset.roundToInt()) }
            .draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState { delta ->
                    dragOffset += delta

                    val movedSlots = (dragOffset / itemHeightPx).toInt()
                    val targetIndex = (currentIndex + movedSlots)
                        .coerceIn(0, list.lastIndex)

                    if (targetIndex != currentIndex) {
                        list.removeAt(currentIndex)
                        list.add(targetIndex, item)
                        currentIndex = targetIndex
                        dragOffset = 0f
                    }
                },
                onDragStopped = {
                    dragOffset = 0f
                }
            )
            .padding(vertical = 8.dp)
            .clickable(enabled = !isEditing) {
                navController.navigate(Screen.Detail.passId(item.id))
            },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(modifier = Modifier.weight(1f)) {
                if (isEditing) {
                    OutlinedTextField(
                        value = editedText,
                        onValueChange = { editedText = it },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            IconButton(onClick = {
                if (isEditing) {
                    if (editedText.isNotBlank()) {
                        listNameViewModel.update(
                            item.copy(name = editedText.trim())
                        )
                        isEditing = false
                    } else {
                        Toast.makeText(context, "List Name Cannot be Empty", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    editedText = item.name
                    isEditing = true
                }
            }) {
                Icon(
                    painter = painterResource(
                        if (isEditing) R.drawable.outline_edit_off
                        else R.drawable.outline_edit_24
                    ),
                    contentDescription = "Edit"
                )
            }

            IconButton(onClick = onTrashClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Red
                )
            }
        }
    }
}
