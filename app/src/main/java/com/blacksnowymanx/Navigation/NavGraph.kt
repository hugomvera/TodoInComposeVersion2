package com.blacksnowymanx.Navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.blacksnowymanx.todoincomposeversion2.roomListNames.ListNameViewModel
import com.blacksnowymanx.todoincomposeversion2.room.TaskViewModel


@Composable
fun SetupNavGraph(
    navController: NavHostController
    , taskViewModel: TaskViewModel
    , listNameViewModel: ListNameViewModel
){
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ){
        composable(route = Screen.Home.route){
            HomeScreen(navController,listNameViewModel)
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument(Detail_ARGUMENT_KEY) {
                type = NavType.IntType })
        ){
            backStackEntry ->
            val id = backStackEntry.arguments?.getInt(Detail_ARGUMENT_KEY) ?: -1
            DetailScreen(navController, taskViewModel, listNameViewModel, id)
        }

    }
}