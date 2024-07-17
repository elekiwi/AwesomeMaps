package com.elekiwi.awesomemaps.model

import com.google.android.gms.maps.model.LatLng

data class MarkerModel(
    val name: String,
    val coordinates: LatLng,
    val snippet: String
)
