package com.blacksnowymanx.Navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.blacksnowymanx.todoincomposeversion2.viewmodel.TaskViewModel

@Composable
fun HomeScreen(navController: NavHostController, taskViewModel: TaskViewModel){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(
            modifier = Modifier.clickable {
                //navController.navigate(Screen.Detail.route)
                navController.navigate(Screen.Detail.passId(5))
                // navController.navigate(Screen.Detail.route)
            },
            text = "Home",
            color = MaterialTheme.colorScheme.primary,
            //fontSize = MaterialTheme.typography.h3.fontSize,
            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
            fontWeight = FontWeight.Bold

        )
    }


    //this composable function will set up  a way to add a name of a list
    //also add a place to put in a description of the list

    //HomeComp(name = "Android", modifier = Modifier.padding(15.dp),taskViewModel)







}

@Composable
fun HomeComp(name: String, modifier:Modifier = Modifier,taskViewModel:  TaskViewModel) {



    //need this for context it is the curernt one
    val context = LocalContext.current

    // Observe the LiveData
    val taskList  by taskViewModel.allTasks.observeAsState(initial = emptyList())

    //this counter will be used to count
    var counter = remember { mutableIntStateOf(0) }

    //text for later use
    var text = remember { mutableStateOf("") }



    //let us do an observer here



    //this alone will cause an error
    // i cannot just print all of the thi ngs in the tasklist
    //Log.d("TaskApp", taskViewModel.allTasks.toString())
















    TODO("Not yet implemented")
}
