package com.saifer.githubusers.favorite.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.saifer.githubusers.User
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: Favorite)

    @Query("DELETE FROM Favorite WHERE Favorite.id = :id")
    fun delete(id: Int): Int

    @Query("SELECT * from Favorite ORDER BY id ASC")
    fun getAllFavorites(): LiveData<List<Favorite>>

    @Query("SELECT COUNT (*) FROM Favorite where Favorite.id = :id")
    fun checkUser(id: Int): Int
}