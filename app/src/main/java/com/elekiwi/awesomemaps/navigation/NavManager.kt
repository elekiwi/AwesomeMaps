package com.elekiwi.awesomemaps.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.elekiwi.awesomemaps.presentation.LaunchScreen
import com.elekiwi.awesomemaps.presentation.camera_screen.CameraViewModel
import com.elekiwi.awesomemaps.presentation.camera_screen.TakePhotoScreen
import com.elekiwi.awesomemaps.presentation.login.LoginViewModel
import com.elekiwi.awesomemaps.presentation.login.views.TabsView
import com.elekiwi.awesomemaps.presentation.maps.MapsViewModel

@Composable
fun NavManager(mapsViewModel: MapsViewModel, loginViewModel: LoginViewModel) {

    val navigationController = rememberNavController()
    NavHost(navController = navigationController, startDestination = Routes.LaunchScreen.route) {
        composable(Routes.LaunchScreen.route){
            LaunchScreen(navigationController)
        }
        composable(Routes.HomeScreen.route){
            MyNavigationDrawer()
        }

        composable(Routes.LoginScreen.route){
            TabsView(navigationController, loginViewModel)
        }

        composable(Routes.TakePhotoScreen.route) {
            TakePhotoScreen(navigationController = navigationController, cameraViewModel = CameraViewModel())
        }
    }
}