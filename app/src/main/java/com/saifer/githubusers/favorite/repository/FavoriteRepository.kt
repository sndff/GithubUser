package com.saifer.githubusers.favorite.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.saifer.githubusers.User
import com.saifer.githubusers.favorite.database.Favorite
import com.saifer.githubusers.favorite.database.FavoriteDao
import com.saifer.githubusers.favorite.database.FavoriteRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavDao = db.favDao()
    }

    fun getAllNotes(): LiveData<List<Favorite>> = mFavDao.getAllFavorites()

    fun insert(favorite: Favorite) {
        executorService.execute { mFavDao.insert(favorite) }
    }

    fun delete(id: Int) {
        executorService.execute { mFavDao.delete(id) }
    }

    fun checkUser(id: Int): Int {
        return mFavDao.checkUser(id)
    }
}