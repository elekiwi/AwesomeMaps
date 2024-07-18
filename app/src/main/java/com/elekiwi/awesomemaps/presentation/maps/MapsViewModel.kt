package com.elekiwi.awesomemaps.presentation.maps

import androidx.lifecycle.ViewModel
import com.elekiwi.awesomemaps.model.MarkerModel
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MapsViewModel: ViewModel() {
    private val firestore: FirebaseFirestore = Firebase.firestore

    fun addMarker(markerData: MarkerModel, onComplete: (Boolean) -> Unit) {
        val marker = mapOf(
            "position" to GeoPoint(markerData.position.latitude, markerData.position.longitude),
            "title" to markerData.title,
            "snippet" to markerData.snippet
        )
        firestore.collection("markers")
            .add(marker)
            .addOnSuccessListener {
                onComplete(true)
            }
            .addOnFailureListener {
                onComplete(false)
            }
    }

    fun getMarkers(onMarkersLoaded: (List<MarkerModel>) -> Unit) {
        firestore.collection("markers")
            .get()
            .addOnSuccessListener { result ->
                val markers = result.map { document ->
                    val position = document.getGeoPoint("position")
                    MarkerModel(
                        id = document.id,
                        position = LatLng(position!!.latitude, position.longitude),
                        title = document.getString("title") ?: "",
                        snippet = document.getString("snippet") ?: ""
                    )
                }
                onMarkersLoaded(markers)
            }
    }

    fun deleteMarker(markerId: String, onComplete: (Boolean) -> Unit) {
        firestore.collection("markers")
            .document(markerId)
            .delete()
            .addOnSuccessListener {
                onComplete(true)
            }
            .addOnFailureListener {
                onComplete(false)
            }
    }

    fun updateMarker(markerData: MarkerModel, onComplete: (Boolean) -> Unit) {
        val marker = mapOf(
            "position" to GeoPoint(markerData.position.latitude, markerData.position.longitude),
            "title" to markerData.title,
            "snippet" to markerData.snippet
        )
        firestore.collection("markers")
            .document(markerData.id)
            .set(marker)
            .addOnSuccessListener {
                onComplete(true)
            }
            .addOnFailureListener {
                onComplete(false)
            }
    }

}