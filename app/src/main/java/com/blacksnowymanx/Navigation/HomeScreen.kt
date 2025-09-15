package com.blacksnowymanx.Navigation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.blacksnowymanx.todoincomposeversion2.R
import com.blacksnowymanx.todoincomposeversion2.roomListNames.ListName
import com.blacksnowymanx.todoincomposeversion2.roomListNames.ListNameViewModel

@Composable
fun HomeScreen(navController: NavHostController, listNameViewModel: ListNameViewModel) {
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        // This is a clickable text for navigation, currently navigating to a placeholder detail screen
//        Text(
//            modifier = Modifier.clickable {
//                navController.navigate(Screen.Detail.passId(5))
//            },
//            text = "Home",
//            color = MaterialTheme.colorScheme.primary,
//            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
//            fontWeight = FontWeight.Bold
//        )
//    }

    // Main content of the Home screen
    HomeComp(navController,listNameViewModel)
}

@Composable
fun HomeComp(navController: NavHostController,listNameViewModel: ListNameViewModel) {
    //this is to get the listname
    val listNameList by listNameViewModel.allListNames.observeAsState(emptyList())




    val context = LocalContext.current
    var listNameText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        // Input field and button to add a new list name
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = listNameText,
                onValueChange = { listNameText = it },
                label = { Text("Enter the name of your List") },
                placeholder = { Text("List Name ...") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(20.dp))

            Button(
                onClick = {
                    if (listNameText.isNotBlank()) {
                        val listNameIn = ListName(name = listNameText, description = "")
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
                ListNameCard(listNameViewModel,navController,
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
fun ListNameCard(listNameViewModel: ListNameViewModel,navController: NavHostController,
    listName: ListName,
    onTrashClick: () -> Unit
) {
    val context = LocalContext.current

    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                Toast
                    .makeText(context, "Clicked: ${listName.name}", Toast.LENGTH_SHORT)
                    .show()
                navController.navigate(Screen.Detail.passId(listName.id))

            },
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = listName.name,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(4.dp))
//                Text(
//                    text = listName.description,
//                    style = MaterialTheme.typography.bodyMedium,
//                    color = Color.DarkGray
//                )
            }

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



// The TaskCard and the second ListNameCard function were either incomplete or duplicates.
// I've removed them to fix the compilation errors.