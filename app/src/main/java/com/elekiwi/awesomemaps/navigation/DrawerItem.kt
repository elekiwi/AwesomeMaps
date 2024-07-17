package com.elekiwi.awesomemaps.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector

enum class DrawerItem(
    val icon: ImageVector,
    val text: String,
    val route: String
) {
    MAPS(Icons.Default.Home, "Maps", "maps_screen"),
    LIST(Icons.Default.List, "List", "list_screen"),
    CAMERA(Icons.Default.Camera, "Camera", "camera_screen"),
}