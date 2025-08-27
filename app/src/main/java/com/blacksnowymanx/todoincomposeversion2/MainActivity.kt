package com.blacksnowymanx.todoincomposeversion2

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.blacksnowymanx.todoincomposeversion2.room.Task
import com.blacksnowymanx.todoincomposeversion2.room.TaskDatabase
import com.blacksnowymanx.todoincomposeversion2.room.TaskViewModelFactory
import com.blacksnowymanx.todoincomposeversion2.ui.theme.TodoInComposeVersion2Theme
import com.blacksnowymanx.todoincomposeversion2.viewmodel.TaskViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.blacksnowymanx.Navigation.SetupNavGraph
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
                TodoInComposeVersion2Theme{
                    val navController = rememberNavController()
                    SetupNavGraph(navController = navController,taskViewModel,listNameViewModel)
                }
        }
    }
}


