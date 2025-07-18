package com.blacksnowymanx.Navigation


import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.blacksnowymanx.todoincomposeversion2.viewmodel.TaskViewModel


@Composable
fun SetupNavGraph(
    navController: NavHostController
    , taskViewModel: TaskViewModel
){
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ){
        composable(route = Screen.Home.route){
            HomeScreen(navController,taskViewModel)
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument(Detail_ARGUMENT_KEY) {
                type = NavType.IntType
            })
        ){
            Log.d("Args", it.arguments?.getInt(Detail_ARGUMENT_KEY).toString())
            DetailScreen(navController,taskViewModel=taskViewModel)
        }

    }
}