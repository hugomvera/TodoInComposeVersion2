package com.blacksnowymanx.Navigation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField

import androidx.compose.ui.unit.dp
import com.blacksnowymanx.todoincomposeversion2.room.Task

@Composable
fun HomeScreen(navController: NavHostController){

    //this is for the context
    val context = LocalContext.current

    var counter = remember { mutableIntStateOf(0) }
    var text = remember { mutableStateOf("") }


    // This is were we place the Top margin so it does not get in the way of the camera
    val padding = 25.dp

    //in this column there will be a button and input field where we will add a button and then input field

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(16.dp)
    ) {
        Row(){
        OutlinedTextField(
            value = text.value,
            onValueChange = { text.value = it },
            label = { Text("Enter name of List") },
            placeholder = { Text("List Name (Grocery list?) ...") },
            singleLine = true,
            modifier = Modifier
                .weight(1f),



        )

        Spacer(modifier = Modifier.width(20.dp))

        Button(onClick = {




            //when; it is not empty then add it to the list

            if(text.value.isNotBlank()){
                val taskTest = Task(
                    //will let room auto generate the id
                    title = "Todo 1",
                    description = text.value,
                    isCompleted = false)
                //taskViewModel.insert(taskTest)
            }else {
                Toast.makeText(context, "Task Cannot be Empty", Toast.LENGTH_SHORT).show()
            }



            //TODO There somekind of error where if there is a
            //Todo Add change to diffierent langauages i guyess
            //Todo the input line width
            //TODo ADD navigation

            //TODO when i hit add make sure that it adds it to the sql database the new descpirtion
            //I do want a new table and take it from there
            //somehow add a table that makes only strings

            //TODO change the existing database where it will have also a title so add a column string
            //I do not think having just one databse will work





        }) {
            Text("Add")
        }}


        // Observe the LiveData
        //TODO Need to change this so it will have a table list
        //val taskList  by taskViewModel.allTasks.observeAsState(initial = emptyList())

        //lets add a list for now that is temporary
        val taskList = listOf(
            "Groceries List"
            ,"Porject tasks"
            ,"home tasks"
            ,"math list that i need to learn"
            ,"Groceries List"
            ,"Porject tasks"
            ,"home tasks"
            ,"math list that i need to learn"
            ,"math list that i need to learn"
            ,"Groceries List"
            ,"Porject tasks"
            ,"home tasks"
            ,"math list that i need to learn"

        )


        //Lets make a Lazycolumn and itereate throught the list and make a card function also for the List
        LazyColumn {
            items(taskList){
                    item -> CardHomeList(item)
            }
        }


    }







//will need to dissect this

//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ){    //lets add a list for now that is temporary
//
//        Text(
//            modifier = Modifier.clickable {
//                //navController.navigate(Screen.Detail.route)
//                navController.navigate(Screen.Detail.passId(5))
//                // navController.navigate(Screen.Detail.route)
//            },
//            text = "Home",
//            color = MaterialTheme.colorScheme.primary,
//            //fontSize = MaterialTheme.typography.h3.fontSize,
//            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
//            fontWeight = FontWeight.Bold
//
//        )
//    }
}

@Composable
fun CardHomeList(title: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp), // Adjust corner radius as needed
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall, // Adjust text style as needed
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp)) // Small space between title and description
            Text(
                text = "Description", // Placeholder description
                style = MaterialTheme.typography.bodyMedium, // Adjust text style as needed
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCardHomeList() {
    Column {
        CardHomeList(title = "Title")
        CardHomeList(title = "Title 2")
    }
}