package com.blacksnowymanx.navigation

import androidx.compose.runtime.livedata.observeAsState
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import com.blacksnowymanx.todoincomposeversion2.R
import com.blacksnowymanx.todoincomposeversion2.room.Task
import com.blacksnowymanx.todoincomposeversion2.roomListNames.ListNameViewModel
import com.blacksnowymanx.todoincomposeversion2.room.TaskViewModel

//Todo maybe change the name
@Composable
fun DetailScreen(
    navController: NavHostController,
    taskViewModel: TaskViewModel,
    listNameViewModel: ListNameViewModel,
    id: Int
){
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        Toast.makeText(context, "ID passed: $id", Toast.LENGTH_SHORT).show()
    }

    //TODO : Change the name of this
    Greeting(
        name = "Android",
        modifier = Modifier.padding(15.dp),
        taskViewModel = taskViewModel,
        listNameViewModel = listNameViewModel,
        id = id
    )
}

//TODO CHANget this name to something else
@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
    taskViewModel: TaskViewModel,
    listNameViewModel: ListNameViewModel,
    id: Int
) {
    //this is for the context not sure
    val context = LocalContext.current

    //this is gets all the listNames might not needed here
    val listName by listNameViewModel.getById(id).observeAsState("Loading...")

    //this gets the list of tasks for all
    //TODO might want to only load the ones that are for the listname passed in
    //URGENT to get this right
   // val taskList by taskViewModel.allTasks.observeAsState(emptyList())
    val taskList by taskViewModel.getTasksByListName(listName).observeAsState(emptyList())


    var text = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(30.dp))
        Text(listName, fontSize = 30.sp)

        Row {
            OutlinedTextField(
                value = text.value,
                onValueChange = { text.value = it },
                label = { Text("Enter your Task") },
                placeholder = { Text("Task ...") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(20.dp))
            Button(onClick = {
                if (text.value.isNotBlank()) {
                    taskViewModel.insert(Task(title = text.value, listName = listName, isCompleted = false))
                    text.value = "" // clear input after adding
                } else {
                    Toast.makeText(context, "Task Cannot be Empty", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        //here is where the list of tasks goes and every element is passed
        //could maybe filter it here
        LazyColumn {
            items(taskList) { item ->
                TaskCard(
                    task = item,
                    onCheckedChange = { checked ->
                        // Toggle completion
                        taskViewModel.update(item.copy(isCompleted = checked))
                    },
                    onUpdate = { newText ->
                        // Update task title
                        if (newText.isNotBlank()) {
                            taskViewModel.update(item.copy(title = newText))
                        }
                    },
                    onDelete = {
                        // Delete task
                        taskViewModel.delete(item)
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
    onUpdate: (String) -> Unit = {},
    onDelete: () -> Unit = {}
) {
    var isToggled by rememberSaveable { mutableStateOf(task.isCompleted) } // checkbox state
    var isEditing by rememberSaveable { mutableStateOf(false) }            // edit mode
    var editedText by rememberSaveable { mutableStateOf(task.title) }      // editable text

    Spacer(modifier = Modifier.height(10.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Checkbox
        IconButton(
            modifier = Modifier.width(40.dp),
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
                contentDescription = "Toggle Task"
            )
        }

        // Text or Editable Field
        if (isEditing) {
            // ---- EDIT MODE ----
            OutlinedTextField(
                value = editedText,
                onValueChange = { editedText = it },
                singleLine = true,
                modifier = Modifier.weight(1f),
                placeholder = { Text("Edit task") }
            )
        } else {
            // ---- DISPLAY MODE ----
            Text(
                text = task.title,
                modifier = Modifier.weight(1f),
                fontSize = 18.sp,
                style = if (isToggled) androidx.compose.ui.text.TextStyle(
                    textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough
                ) else androidx.compose.ui.text.TextStyle.Default
            )
        }

        // Edit / Save button
        IconButton(onClick = {
            if (isEditing) {
                // ---- SAVE ACTION ----
                onUpdate(editedText)
            }
            isEditing = !isEditing
        }) {
            Icon(
                painter = if (isEditing)
                    painterResource(id = R.drawable.outline_edit_24)
                else
                    painterResource(id = R.drawable.outline_edit_24),
                contentDescription = if (isEditing) "Save" else "Edit Task"
            )
        }

        // Delete button
        IconButton(onClick = onDelete) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_cancel_24),
                contentDescription = "Delete Task"
            )
        }
    }
}
