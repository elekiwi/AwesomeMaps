package com.elekiwi.awesomemaps.model

import com.google.android.gms.maps.model.LatLng

data class MarkerModel(
    val id: String = "",
    val position: LatLng,
    val title: String,
    val snippet: String
) {
    constructor() : this("", LatLng(0.0, 0.0), "", "")
}
