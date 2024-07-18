package com.elekiwi.awesomemaps.presentation.maps

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.elekiwi.awesomemaps.model.MarkerModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun MapsScreen(mapsViewModel: MapsViewModel) {

    var latLngCreated: LatLng = LatLng(41.4534265, 2.1837151)


    var markers by remember { mutableStateOf(listOf<MarkerModel>()) }
    var showDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var newMarkerPosition by remember { mutableStateOf<LatLng?>(null) }
    var selectedMarker by remember { mutableStateOf<MarkerModel?>(null) }
    var markerTitle by remember { mutableStateOf("") }
    var markerSnippet by remember { mutableStateOf("") }
    var markerToDelete by remember { mutableStateOf<MarkerModel?>(null) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LaunchedEffect(Unit) {
            mapsViewModel.getMarkers { loadedMarkers ->
                markers = loadedMarkers
            }
        }
        val itb = LatLng(41.4534265, 2.1837151)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(itb, 10f)
        }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            //properties = MapProperties(mapType = MapType.HYBRID),
            onMapClick = {latLng ->
                Log.e("latlangcreated", "MapsScreen: $latLng")
                newMarkerPosition = latLng
                showDialog = true


            }
        ){



            Marker(
                state = MarkerState(position = itb),
                title = "ITB",
                snippet = "Market at ITB"
            )

            /*markers.forEach{ marker ->
                marker.let {markerModel ->
                    Marker(
                        state = MarkerState(position = markerModel!!.position),
                        title = markerModel.title,
                        snippet = markerModel.snippet
                    )
                }
            }*/

            markers.forEach { markerData ->
                CustomMarker(markerData) { selectedMarkerData ->
                    selectedMarker = selectedMarkerData
                    markerTitle = selectedMarkerData.title
                    markerSnippet = selectedMarkerData.snippet
                    showEditDialog = true
                }
            }


        }



        if (showDialog && newMarkerPosition != null) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    Button(onClick = {
                        val newMarker = MarkerModel(
                            position = newMarkerPosition!!,
                            title = markerTitle,
                            snippet = markerSnippet
                        )
                        mapsViewModel.addMarker(newMarker) { success ->
                            if (success) {
                                mapsViewModel.getMarkers { loadedMarkers ->
                                    markers = loadedMarkers
                                }
                            }
                            showDialog = false
                        }
                    }) {
                        Text("Add Marker")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("Cancel")
                    }
                },
                title = { Text("Add Marker") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = markerTitle,
                            onValueChange = { markerTitle = it },
                            label = { Text("Title") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = markerSnippet,
                            onValueChange = { markerSnippet = it },
                            label = { Text("Snippet") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            )
        }

        if (showEditDialog && selectedMarker != null) {
            AlertDialog(
                onDismissRequest = { showEditDialog = false },
                confirmButton = {
                    Button(onClick = {
                        val updatedMarker = selectedMarker!!.copy(
                            title = markerTitle,
                            snippet = markerSnippet
                        )
                         mapsViewModel.updateMarker(updatedMarker) { success ->
                            if (success) {
                                mapsViewModel.getMarkers { loadedMarkers ->
                                    markers = loadedMarkers
                                }
                            }
                            showEditDialog = false
                        }
                    }) {
                        Text("Save Changes")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        mapsViewModel.deleteMarker(selectedMarker!!.id) { success ->
                            if (success) {
                                mapsViewModel.getMarkers { loadedMarkers ->
                                    markers = loadedMarkers
                                }
                            }
                            showEditDialog = false
                        }
                    }) {
                        Text("Delete Marker")
                    }
                },
                title = { Text("Edit Marker") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = markerTitle,
                            onValueChange = { markerTitle = it },
                            label = { Text("Title") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = markerSnippet,
                            onValueChange = { markerSnippet = it },
                            label = { Text("Snippet") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            )
        }

    }
}
@Composable
fun CustomMarker(markerData: MarkerModel, onClick: (MarkerModel) -> Unit) {
    Marker(
        state = MarkerState(position = markerData.position),
        title = markerData.title,
        snippet = markerData.snippet,
        onClick = {
            onClick(markerData)
            true
        }
    )
}
@Composable
fun MyGoogleMaps() {

}
@Composable
fun MinimalDialog(latLng: LatLng, onDismissRequest: () -> Unit, onMarkerAdded: (MarkerModel) -> Unit) {
    var myTitle by remember { mutableStateOf("") }
    var mySnippet by remember { mutableStateOf("") }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {

            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)) {
                TextField(
                    value = myTitle,
                    onValueChange = { myTitle = it },
                    modifier = Modifier.padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        //backgroundColor = Color(0xFFFAFAFA),
                        //focusedIndicatorColor = Color.Transparent, //hide the indicator
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = Color(0xFFFAFAFA)
                    )
                )
                TextField(
                    value = mySnippet,
                    onValueChange = { mySnippet = it },
                    modifier = Modifier.padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        //backgroundColor = Color(0xFFFAFAFA),
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = Color(0xFFFAFAFA)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    //val marker = MarkerModel(myTitle, latLng, mySnippet)
                   // onMarkerAdded(marker)
                }) {
                    Text(text = "Añadir marker")
                }
            }

        }
    }
}