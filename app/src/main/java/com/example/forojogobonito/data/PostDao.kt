package com.example.forojogobonito.data


import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Query("SELECT * FROM posts ORDER BY id DESC")
    fun obtenerPosts(): Flow<List<Post>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarPost(post: Post)

    @Update
    suspend fun actualizarPost(post: Post)

    @Delete
    suspend fun eliminarPost(post: Post)
}
