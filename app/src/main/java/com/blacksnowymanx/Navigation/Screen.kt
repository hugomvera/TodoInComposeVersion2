package com.blacksnowymanx.Navigation


const val Detail_ARGUMENT_KEY = "id"
sealed class Screen(val route: String) {
    object Home : Screen(route = "home_screen")
    object Detail : Screen(route = "detail_screen/{$Detail_ARGUMENT_KEY}")
    {
        fun passId(id: Int): String {
            return "detail_screen/$id"
        }

    }
}