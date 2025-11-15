package com.blacksnowymanx.todoincomposeversion2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.blacksnowymanx.todoincomposeversion2.room.TaskDatabase
import com.blacksnowymanx.todoincomposeversion2.room.TaskViewModelFactory
import com.blacksnowymanx.todoincomposeversion2.ui.theme.TodoInComposeVersion2Theme
import com.blacksnowymanx.todoincomposeversion2.room.TaskViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.blacksnowymanx.navigation.SetupNavGraph
import com.blacksnowymanx.todoincomposeversion2.roomListNames.ListNameViewModel
import com.blacksnowymanx.todoincomposeversion2.roomListNames.ListNameViewModelFactory

class MainActivity : ComponentActivity() {
    //this is where our taskViewModel is at
    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory(TaskDatabase.getDatabase(this).taskDao())
    }

    private val listNameViewModel: ListNameViewModel by viewModels {
        ListNameViewModelFactory(TaskDatabase.getDatabase(this).listNameDao())
    }


    lateinit var navController: NavHostController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            //this sets a theme for the app
                TodoInComposeVersion2Theme{
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        //might want to have a if statement here where if its on dark mode
                        //then black bakcground
                        //if it is on day mode then white background
                        //color= Color.White
                    ){
                    val navController = rememberNavController()
                    SetupNavGraph(navController = navController,taskViewModel,listNameViewModel)
                }}
        }
    }
}


