package com.saifer.githubusers

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var rvUser: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvUser = findViewById(R.id.rv_user)
        rvUser.setHasFixedSize(true)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // show similar list users
                val searchList = ArrayList<User>()
                // from json
                val client = ApiConfig.getApiService().getUser(query)
                client.enqueue(object : Callback<FindUserResponse> {
                    override fun onResponse(
                        call: Call<FindUserResponse>,
                        response: Response<FindUserResponse>
                    ) {
//                        showLoading(false)
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
                                showDetailUser(searchList)
                            }
                        } else {
                            Log.e("MainActivity", "onFailure: ${response.message()}")
                        }
                    }
                    override fun onFailure(call: Call<FindUserResponse>, t: Throwable) {
//                        showLoading(false)
                        Log.e("MainActivity", "onFailure: ${t.message}")
                    }
                })
                showDetailUser(searchList)
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    private fun showDetailUser(data: ArrayList<User>) {
        if(applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            rvUser.layoutManager = GridLayoutManager(this, 2)
        } else {
            rvUser.layoutManager = LinearLayoutManager(this)
        }
        val listUserAdapter = ListUserAdapter(data)
        rvUser.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showSelectedUser(data)
            }
        })
    }


    private fun showSelectedUser(user: User) {
        val intentDetailUserActivity = Intent(this@MainActivity, DetailUserActivity::class.java)
        intentDetailUserActivity.putExtra(DetailUserActivity.EXTRA_USER, user)
        startActivity(intentDetailUserActivity)
    }

//    private fun showLoading(isLoading: Boolean) {
//        if (isLoading) {
//            binding.progressBar.visibility = View.VISIBLE
//        } else {
//            binding.progressBar.visibility = View.GONE
//        }
//    }
}