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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.blacksnowymanx.todoincomposeversion2.R
import com.blacksnowymanx.todoincomposeversion2.room.Task
import com.blacksnowymanx.todoincomposeversion2.room.TaskViewModel
import com.blacksnowymanx.todoincomposeversion2.roomListNames.ListNameViewModel
import kotlin.math.roundToInt

@Composable
fun DetailScreen(
    navController: NavHostController,
    taskViewModel: TaskViewModel,
    listNameViewModel: ListNameViewModel,
    id: Int
) {
    val context = LocalContext.current

//    LaunchedEffect(Unit) {
//        Toast.makeText(context, "ID passed: $id", Toast.LENGTH_SHORT).show()
//    }

    TaskListScreen(
        taskViewModel = taskViewModel,
        listNameViewModel = listNameViewModel,
        id = id
    )
}

@Composable
fun TaskListScreen(
    taskViewModel: TaskViewModel,
    listNameViewModel: ListNameViewModel,
    id: Int
) {
    val context = LocalContext.current

    val listName by listNameViewModel.getById(id).observeAsState("Loading...")
    val dbTasks by taskViewModel.getTasksByListName(listName).observeAsState(emptyList())

    val taskList = remember { mutableStateListOf<Task>() }

    LaunchedEffect(dbTasks) {
        taskList.clear()
        taskList.addAll(dbTasks)
    }

    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(30.dp))
        Text(listName, fontSize = 30.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Enter your Task") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(20.dp))

            Button(onClick = {
                if (text.isNotBlank()) {
                    taskViewModel.insert(
                        Task(
                            title = text.trim(),
                            listName = listName,
                            isCompleted = false
                        )
                    )
                    text = ""
                } else {
                    Toast.makeText(context, "Task Cannot be Empty", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn {
            itemsIndexed(
                items = taskList,
                key = { _, task -> task.id }
            ) { index, task ->
                ReorderableTaskCard(
                    startIndex = index,
                    task = task,
                    taskList = taskList,
                    onCheckedChange = {
                        taskViewModel.update(task.copy(isCompleted = it))
                    },
                    onUpdate = {
                        if (it.isNotBlank()) {
                            taskViewModel.update(task.copy(title = it))
                        }
                    },
                    onDelete = {
                        taskViewModel.delete(task)
                    }
                )
            }
        }
    }
}

@Composable
fun ReorderableTaskCard(
    startIndex: Int,
    task: Task,
    taskList: MutableList<Task>,
    onCheckedChange: (Boolean) -> Unit,
    onUpdate: (String) -> Unit,
    onDelete: () -> Unit
) {
    var isCompleted by rememberSaveable { mutableStateOf(task.isCompleted) }
    var isEditing by rememberSaveable { mutableStateOf(false) }
    var editedText by rememberSaveable { mutableStateOf(task.title) }

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
                        .coerceIn(0, taskList.lastIndex)

                    if (targetIndex != currentIndex) {
                        taskList.removeAt(currentIndex)
                        taskList.add(targetIndex, task)
                        currentIndex = targetIndex
                        dragOffset = 0f
                    }
                },
                onDragStopped = { dragOffset = 0f }
            )
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = {
                isCompleted = !isCompleted
                onCheckedChange(isCompleted)
            }) {
                Icon(
                    painter = painterResource(
                        if (isCompleted)
                            R.drawable.baseline_check_box_24
                        else
                            R.drawable.baseline_check_box_outline_blank_24
                    ),
                    contentDescription = "Toggle Task"
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            if (isEditing) {
                OutlinedTextField(
                    value = editedText,
                    onValueChange = { editedText = it },
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
            } else {
                Text(
                    text = task.title,
                    modifier = Modifier.weight(1f),
                    fontSize = 18.sp,
                    textDecoration = if (isCompleted)
                        TextDecoration.LineThrough
                    else
                        TextDecoration.None
                )
            }

            IconButton(onClick = {
                if (isEditing) {
                    onUpdate(editedText)
                }
                isEditing = !isEditing
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_edit_24),
                    contentDescription = "Edit Task"
                )
            }

            IconButton(onClick = onDelete) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_cancel_24),
                    contentDescription = "Delete Task",
                    tint = Color.Red
                )
            }
        }
    }
}
