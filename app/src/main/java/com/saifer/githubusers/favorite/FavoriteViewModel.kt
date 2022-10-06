package com.saifer.githubusers.favorite

import android.app.Application
import android.content.Intent
import android.content.res.Configuration
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saifer.githubusers.User
import com.saifer.githubusers.adapter.ListUserAdapter
import com.saifer.githubusers.databinding.ActivityMainBinding
import com.saifer.githubusers.detail.DetailUserActivity
import com.saifer.githubusers.favorite.repository.FavoriteRepository

class FavoriteViewModel(application: Application): ViewModel() {

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun showUser(activity: AppCompatActivity, rv: RecyclerView, data: ArrayList<User>) {
        if(activity.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            rv.layoutManager = GridLayoutManager(activity, 2)
        } else {
            rv.layoutManager = LinearLayoutManager(activity)
        }
        val listUserAdapter = ListUserAdapter(data)
        rv.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showSelectedUser(activity, data)
            }
        })
    }

    fun showSelectedUser(activity: AppCompatActivity, user: User) {
        val intentDetailUserActivity = Intent(activity, DetailUserActivity::class.java)
        intentDetailUserActivity.putExtra(DetailUserActivity.EXTRA_USER, user)
        activity.startActivity(intentDetailUserActivity)
    }

    fun getAllFavorites(){
        mFavoriteRepository.getAllNotes()
    }

    fun showLoading(binding: ActivityMainBinding, isLoading: Boolean) {
        if (isLoading) {
            val progressBar = binding.progressBar
            progressBar.visibility = View.VISIBLE
        } else {
            val progressBar = binding.progressBar
            progressBar.visibility = View.GONE
        }
    }
}