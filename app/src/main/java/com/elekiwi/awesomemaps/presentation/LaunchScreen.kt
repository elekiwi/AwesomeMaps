package com.elekiwi.awesomemaps.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.elekiwi.awesomemaps.navigation.DrawerItem
import com.elekiwi.awesomemaps.navigation.Routes
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LaunchScreen(navController: NavController){

    LaunchedEffect(Unit){
        if (!FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()){
            navController.navigate(Routes.HomeScreen.route)

        }else{
           navController.navigate(Routes.LoginScreen.route)


        }
    }

}