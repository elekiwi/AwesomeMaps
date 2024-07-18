package com.elekiwi.awesomemaps.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.elekiwi.awesomemaps.navigation.MyNavigationDrawer
import com.elekiwi.awesomemaps.navigation.NavManager
import com.elekiwi.awesomemaps.presentation.login.LoginViewModel
import com.elekiwi.awesomemaps.presentation.maps.MapsViewModel
import com.elekiwi.awesomemaps.ui.theme.AwesomeMapsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val loginViewModel : LoginViewModel by viewModels()
        val mapsViewModel : MapsViewModel by viewModels()
        setContent {
            AwesomeMapsTheme {
                //MyNavigationDrawer(mapsViewModel, loginViewModel)
                NavManager(mapsViewModel = mapsViewModel, loginViewModel = loginViewModel)
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AwesomeMapsTheme {
    }
}