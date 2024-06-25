package com.elekiwi.awesomemaps.navigation

sealed class Routes(val route: String) {
    object TakePhotoScreen: Routes("takephoto_screen")

}