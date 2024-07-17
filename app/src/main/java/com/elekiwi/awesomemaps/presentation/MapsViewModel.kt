package com.elekiwi.awesomemaps.presentation

import androidx.lifecycle.ViewModel
import com.elekiwi.awesomemaps.model.MarkerModel
import com.google.android.gms.maps.model.Marker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MapsViewModel: ViewModel() {
    val markers: Array<Marker>? = null

    private val _marker = MutableStateFlow<MarkerModel?>(null)
    val marker = _marker.asStateFlow()

}