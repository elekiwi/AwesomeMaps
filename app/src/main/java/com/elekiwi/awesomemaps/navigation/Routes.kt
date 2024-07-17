package com.elekiwi.awesomemaps.navigation

sealed class Routes(val route: String) {
    object TakePhotoScreen: Routes("takephoto_screen")
    object LaunchScreen: Routes("launch_screen")
    object LoginScreen: Routes("login_screen")
    object HomeScreen: Routes("home_screen")

}