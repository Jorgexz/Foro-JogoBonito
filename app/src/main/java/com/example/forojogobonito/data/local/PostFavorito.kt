package com.example.forojogobonito.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoritos")
data class PostFavorito(
    @PrimaryKey val id: Int,
    val titulo: String,
    val contenido: String,
    val autor: String
)