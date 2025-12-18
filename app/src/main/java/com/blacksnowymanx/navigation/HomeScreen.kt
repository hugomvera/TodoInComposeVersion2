package com.blacksnowymanx.navigation

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.blacksnowymanx.todoincomposeversion2.R
import com.blacksnowymanx.todoincomposeversion2.roomListNames.ListName
import com.blacksnowymanx.todoincomposeversion2.roomListNames.ListNameViewModel


//this seems a bit redudent the homescreen is calling the homecomp
//this is the starting  point from the Main activity
//homeScreen has 2 parameters NavController and ListnameViewMode
@Composable
fun HomeScreen(
    navController: NavHostController,
    listNameViewModel: ListNameViewModel
) {
    HomeComp(navController, listNameViewModel)
}

//this is called from HomeComp
//passes 2 parameters the  navcontroler and the viewmodel for the list
@Composable
fun HomeComp(
    navController: NavHostController,
    listNameViewModel: ListNameViewModel
) {
    //This is a the name of the list of the list
    //so it contains  alist of all the names in the the toodo app list
    //its a type of list in its datastucture
    //this val starts out emtpy at firsrt
    val listNameList by listNameViewModel.allListNames.observeAsState(emptyList())

    //self explanatory
    val context = LocalContext.current

    //this it he name of the listName or the text in the row
    var listNameText by remember { mutableStateOf("") }


    //this column will have the button on the top where it adds
    //it will also hav ehte input list
    //later in the code it will call the lazy list where will make the cards
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(horizontal = 16.dp)
    ) {

        //not sure what this space is but its something not in the rows
        Spacer(modifier = Modifier.height(30.dp))

        //this is the top row where the button and the button and input field go
        //TODO change it so its on the top the new item
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Input field and button to add a new list name
            //this is on the very top and its functionality
            //is that it will add a new card or item on the bottom
            OutlinedTextField(
                value = listNameText,
                onValueChange = { listNameText = it },
                label = { Text("Enter the name of your List") },
                placeholder = { Text("List Name ...") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )

            //this is space for the top row between the input field and the button
            Spacer(modifier = Modifier.width(20.dp))

            //this is the button on the top where it adds on the right
            Button(
                onClick = {
                    if (listNameText.isNotBlank()) {
                        val listNameIn = ListName(name = listNameText.trim(), description = "")
                        listNameViewModel.insert(listNameIn)
                        listNameText = "" // Clear the text field after adding
                    } else {
                        Toast.makeText(context, "List Name Cannot be Empty", Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // LazyColumn to display the list of list names
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(listNameList) { item ->
                ListNameCard(
                    listNameViewModel,
                    navController,
                    listName = item,
                    onTrashClick = {
                        listNameViewModel.delete(item)
                    }
                )
            }
        }
    }
}

@Composable
fun ListNameCard(
    listNameViewModel: ListNameViewModel,
    navController: NavHostController,
    listName: ListName,
    onTrashClick: () -> Unit
) {

    //this is for the context
    val context = LocalContext.current

    //this is for knowing if it is being editied
    //that is thje edit button was hit
    var isEditing by remember { mutableStateOf(false) }

    // Initialize editedText with the current listName.name.
    // This is important so the TextField has the correct value when edit mode starts.
    var editedText by remember(listName.name) { mutableStateOf(listName.name) }


    //this is so it is rememebre through the lifcecycle and recomposes
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(enabled = !isEditing) {
                navController.navigate(Screen.Detail.passId(listName.id))
            },
        elevation = CardDefaults.cardElevation(6.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // This Column takes up the remaining space
            Column(
                modifier = Modifier.weight(1f)
            ) {
                if (isEditing) {
                    OutlinedTextField(
                        value = editedText,
                        onValueChange = { editedText = it },
                        singleLine = true,
                        // Use fillMaxWidth() to properly occupy the weighted space.
                        // Removed the redundant .weight(1f) and padding is handled by the parent row's padding.
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Enter new list name") }
                    )
                } else {
                    // Removed the redundant inner Column(modifier = Modifier.weight(1f))
                    Text(
                        text = listName.name,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
                // You can uncomment the description text here if you want to show it:
                /*
                Text(
                    text = listName.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray
                )
                */
            }


            // ---- EDIT / SAVE BUTTON ----
            IconButton(onClick = {
                if (isEditing) {
                    // ---- SAVE ACTION ----
                    if (editedText.isNotBlank()) {
                        // Trim the edited text to remove leading/trailing spaces
                        val updatedList = listName.copy(name = editedText.trim())
                        listNameViewModel.update(updatedList)

                        isEditing = false
                    } else {
                        Toast.makeText(context, "List Name Cannot be Empty", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // ---- ENTER EDIT MODE ----
                    // When entering edit mode, ensure editedText is set to the current list name
                    editedText = listName.name
                    isEditing = true
                }
            }) {
                // Change icon depending on whether editing or saving
                Icon(
                    // You might want to use a 'save' icon instead of 'edit_off' for better clarity
                    painter = painterResource(id = if (isEditing) R.drawable.outline_edit_off  else R.drawable.outline_edit_24),
                    contentDescription = if (isEditing) "Save changes" else "Edit list name"
                )
            }


            // ---- DELETE BUTTON ----
            IconButton(onClick = onTrashClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete List",
                    tint = Color.Red
                )
            }
        }
    }
}