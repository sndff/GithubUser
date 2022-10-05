package com.saifer.githubusers.main

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saifer.githubusers.FindUserResponse
import com.saifer.githubusers.User
import com.saifer.githubusers.adapter.ListUserAdapter
import com.saifer.githubusers.api.ApiConfig
import com.saifer.githubusers.databinding.ActivityMainBinding
import com.saifer.githubusers.detail.DetailUserActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

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

    fun showSelectedUser(activity: AppCompatActivity, user: User) : Intent {
        val intentDetailUserActivity = Intent(activity, DetailUserActivity::class.java)
        intentDetailUserActivity.putExtra(DetailUserActivity.EXTRA_USER, user)
        return intentDetailUserActivity
    }

    fun findUser(activity: AppCompatActivity, binding: ActivityMainBinding, rv: RecyclerView, key: String){
        val searchList = ArrayList<User>()
        val client = ApiConfig.getApiService().getUser(key)
        showLoading(binding, true)
        client.enqueue(object : Callback<FindUserResponse> {
            override fun onResponse(
                call: Call<FindUserResponse>,
                response: Response<FindUserResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        for (i in 0 until responseBody.items!!.size){
                            val user = User(
                                responseBody.items[i]!!.avatarUrl,
                                responseBody.items[i]!!.login,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null
                            )
                            searchList.add(user)
                        }
                        showUser(activity, rv, searchList)
                    }
                    showLoading(binding, false)
                } else {
                    Log.e("MainActivity", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<FindUserResponse>, t: Throwable) {
                Log.e("MainActivity", "onFailure: ${t.message}")
            }
        })
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