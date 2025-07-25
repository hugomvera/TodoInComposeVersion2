package com.blacksnowymanx.todoincomposeversion2

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.blacksnowymanx.todoincomposeversion2.room.Task
import com.blacksnowymanx.todoincomposeversion2.room.TaskDatabase
import com.blacksnowymanx.todoincomposeversion2.room.TaskViewModelFactory
import com.blacksnowymanx.todoincomposeversion2.ui.theme.TodoInComposeVersion2Theme
import com.blacksnowymanx.todoincomposeversion2.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory(TaskDatabase.getDatabase(this).taskDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoInComposeVersion2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding),
                        taskViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
    taskViewModel: TaskViewModel
) {
    val context = LocalContext.current
    val taskList by taskViewModel.allTasks.observeAsState(initial = emptyList())

    var text by remember { mutableStateOf("") }

    Log.d("TaskApp", taskList.toString())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(30.dp))

        Row {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Enter your Task") },
                placeholder = { Text("Task ...") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(20.dp))
            Button(onClick = {
                if (text.isNotBlank()) {
                    val taskTest = Task(
                        // Let Room auto-generate the id
                        title = "Todo 1",  // You might want to update this later
                        description = text,
                        isCompleted = false
                    )
                    taskViewModel.insert(taskTest)
                    text = "" // Clear input after adding
                } else {
                    Toast.makeText(context, "Task Cannot be Empty", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("Add")
            }
        }

        Log.d("Testing", "starting loop")

        LazyColumn {
            items(taskList) { item ->
                TaskCard(
                    task = item,
                    onCheckedChange = { checked ->
                        val updatedTask = item.copy(isCompleted = checked)
                        taskViewModel.update(updatedTask)
                    },
                    onThrashCancel = {
                        taskViewModel.delete(item)
                    },
                    onEditDone = { newDescription ->
                        if (newDescription.isNotBlank() && newDescription != item.description) {
                            val updatedTask = item.copy(description = newDescription)
                            taskViewModel.update(updatedTask)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun TaskCard(
    task: Task,
    onCheckedChange: (Boolean) -> Unit = {},
    onThrashCancel: () -> Unit = {},
    onEditDone: (String) -> Unit = {}
) {
    var isEditing by rememberSaveable { mutableStateOf(false) }
    var editedText by rememberSaveable { mutableStateOf(task.description) }
    var isToggled by rememberSaveable { mutableStateOf(task.isCompleted) }

    Spacer(modifier = Modifier.height(10.dp))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Checkbox Icon
        IconButton(
            modifier = Modifier.weight(0.1f),
            onClick = {
                isToggled = !isToggled
                onCheckedChange(isToggled)
            }
        ) {
            Icon(
                painter = if (isToggled)
                    painterResource(id = R.drawable.baseline_check_box_24)
                else
                    painterResource(id = R.drawable.baseline_check_box_outline_blank_24),
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Editable text or static text
        if (isEditing) {
            OutlinedTextField(
                value = editedText,
                onValueChange = { editedText = it },
                singleLine = true,
                modifier = Modifier.weight(0.7f),
                // Optional: add keyboard actions here if you want
            )
        } else {
            Text(
                text = task.description,
                modifier = Modifier
                    .weight(0.7f)
                    .padding(start = 4.dp),
                textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None
            )
        }

        // Edit/Save icon button
        IconButton(
            onClick = {
                if (isEditing) {
                    // Save changes and exit edit mode
                    onEditDone(editedText)
                    isEditing = false
                } else {
                    // Enter edit mode
                    editedText = task.description
                    isEditing = true
                }
            },
            modifier = Modifier.weight(0.1f)
        ) {
            Icon(
                painter = painterResource(
                    id = if (isEditing)
                        R.drawable.baseline_check_24
                    else
                        R.drawable.baseline_edit_24
                ),
                contentDescription = if (isEditing) "Save" else "Edit"
            )
        }

        // Delete button
        IconButton(
            modifier = Modifier.weight(0.1f),
            onClick = { onThrashCancel() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_cancel_24),
                contentDescription = "Delete"
            )
        }
    }
}