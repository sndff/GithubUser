package com.saifer.githubusers.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.saifer.githubusers.favorite.database.Favorite
import com.saifer.githubusers.favorite.repository.FavoriteRepository

class FavoriteViewModel(application: Application): ViewModel() {

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getAllFavorites() : LiveData<List<Favorite>>{
        return mFavoriteRepository.getAllFavorites()
    }
}