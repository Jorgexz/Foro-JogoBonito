package com.example.forojogobonito.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritosDao {
    @Query("SELECT * FROM favoritos")
    fun obtenerFavoritos(): Flow<List<PostFavorito>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarFavorito(post: PostFavorito)

    @Delete
    suspend fun eliminarFavorito(post: PostFavorito)
}