package com.elekiwi.awesomemaps.presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun MapsScreen(mapsViewModel: MapsViewModel) {


    var markers = mutableListOf<MarkerOptions?>()
    var showDialog by remember {
        mutableStateOf(false)
    }
    var latLngCreated: LatLng = LatLng(41.4534265, 2.1837151)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val itb = LatLng(41.4534265, 2.1837151)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(itb, 10f)
        }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            //properties = MapProperties(mapType = MapType.HYBRID),
            onMapClick = {
                Log.e("latlangcreated", "MapsScreen: $it")
                latLngCreated = it
                showDialog = true
                //create object
                //BottomSheetDefaults

            }
        ){


            //TODO: foreach llistat de marcadors
            Marker(
                state = MarkerState(position = itb),
                title = "ITB",
                snippet = "Market at ITB"
            )

            markers.forEach{marker ->
                // set marker to your google map with it+

                marker.let {
                    Marker(
                        state = MarkerState(position = it!!.position),
                        title = it.title,
                        snippet = it.snippet
                    )
                }

            }





        }

        if(showDialog) {
            Log.e("tag", "MapsScreen2: $latLngCreated ")
            MinimalDialog(latLngCreated, onDismissRequest = {
                showDialog = false

            }) {

                val marker =  MarkerOptions().position(latLngCreated).anchor(
                    0.5f,
                    0.5f
                ).title(it.name).snippet(it.snippet)
                markers.add(marker)
                //añadir marker a la colección de firestore
            }
        }
    }
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
                    val marker = MarkerModel(myTitle, latLng, mySnippet)
                    onMarkerAdded(marker)
                }) {
                    Text(text = "Añadir marker")
                }
            }

        }
    }
}