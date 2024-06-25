package com.elekiwi.awesomemaps.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.elekiwi.awesomemaps.presentation.camera_screen.CameraScreen
import com.elekiwi.awesomemaps.presentation.camera_screen.CameraViewModel
import com.elekiwi.awesomemaps.presentation.MapsScreen
import com.elekiwi.awesomemaps.presentation.camera_screen.TakePhotoScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyNavigationDrawer() {
    val navigationController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItemIndex by remember {
        mutableStateOf(0)
    }

    ModalNavigationDrawer(drawerContent = {
        ModalDrawerSheet {
            Spacer(modifier = Modifier.height(16.dp))
            DrawerItem.entries.forEachIndexed { index, drawerItem ->
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = drawerItem.icon,
                            contentDescription = null
                        )
                    },
                    label = { Text(text = drawerItem.text) },
                    selected = index == selectedItemIndex,
                    onClick = {
                        selectedItemIndex = index
                        scope.launch { drawerState.close() }
                        navigationController.navigate(drawerItem.route) {
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
        }
    }, drawerState = drawerState) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Awesome Maps") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "menu")
                        }
                    }
                )
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                NavHost(navController = navigationController, startDestination = DrawerItem.MAPS.route) {
                    composable(DrawerItem.MAPS.route) {
                        MapsScreen()
                    }
                    composable(DrawerItem.LIST.route) {
                       // AboutScreen(drawerState)
                    }
                    composable(DrawerItem.CAMERA.route) {
                        CameraScreen(
                            navigationController = navigationController,
                            cameraViewModel = CameraViewModel()
                        )
                    }
                    composable(Routes.TakePhotoScreen.route) {
                        TakePhotoScreen(navigationController = navigationController, cameraViewModel = CameraViewModel())
                    }
                }
            }
        }
    }
}