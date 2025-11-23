package com.example.forojogobonito.model

import com.google.gson.annotations.SerializedName

//respuesta general de la API
data class PartidosResponse(
    @SerializedName("events") val eventos: List<Partido>?
)

//Cada partido individual
data class Partido(
    @SerializedName("idEvent") val id: String,
    @SerializedName("strEvent") val nombrePartido: String,
    @SerializedName("dateEvent") val fecha: String,
    @SerializedName("strTime") val hora: String,
    @SerializedName("strLeague") val liga: String,
    @SerializedName("strThumb") val imagenUrl: String?
)