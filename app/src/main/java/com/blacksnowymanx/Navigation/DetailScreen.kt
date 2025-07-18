package com.blacksnowymanx.Navigation


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController

@Composable
fun DetailScreen(navController: NavHostController){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(

            modifier = Modifier.clickable {
                navController.navigate(Screen.Home.route)
                {popUpTo(Screen.Home.route)
                {
                    inclusive = true
                } }
                //navController.popBackStack()
            },
            text = "Deetail",
            color = Color.Red,
            //fontSize = MaterialTheme.typography.h3.fontSize,
            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}

