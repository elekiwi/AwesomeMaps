package com.elekiwi.awesomemaps.presentation.camera_screen

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.navigation.NavHostController
import com.elekiwi.awesomemaps.navigation.DrawerItem
import com.elekiwi.awesomemaps.navigation.Routes

@Composable
fun CameraScreen(navigationController: NavHostController, cameraViewModel: CameraViewModel) {
    val context = LocalContext.current
    val isCameraPermissionGranted by cameraViewModel.cameraPermissionGranted.observeAsState(false)
    val shouldShowPermissionRationale by cameraViewModel.shouldShowPermissionRationale.observeAsState(
        initial = false
    )
    val showPermissionDenied by cameraViewModel.showPermissionDenied.observeAsState(initial = false)

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult =  { isGranted ->
            if (isGranted) {
               cameraViewModel.setCameraPermissionGranted(true)
            } else {
                cameraViewModel.setShouldShowPermissionRationale(
                    shouldShowRequestPermissionRationale(
                        context as Activity,
                        android.Manifest.permission.CAMERA
                    )
                )
                if (!shouldShowPermissionRationale) {
                    Log.i("CameraScreen", "No podemos volver a pedir permisos")
                    cameraViewModel.setShowPermissionDenied(true)
                }
            }
        }
    )


    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Button(onClick = {
            if (!isCameraPermissionGranted) {
                launcher.launch(android.Manifest.permission.CAMERA)
            } else {
               navigationController.navigate(Routes.TakePhotoScreen.route)
            }
        }) {
            Text(text = "Take photo")
        }
    }

    if (showPermissionDenied) {
        PermissionDeclinedScreen()
    }

}