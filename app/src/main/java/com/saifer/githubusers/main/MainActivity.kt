package com.saifer.githubusers.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.saifer.githubusers.R
import com.saifer.githubusers.databinding.ActivityMainBinding
import com.saifer.githubusers.theme.ChangeThemeActivity
import com.saifer.githubusers.theme.SettingPreferences
import com.saifer.githubusers.theme.ThemeViewModel
import com.saifer.githubusers.theme.ThemeViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {

    private lateinit var rvUser: RecyclerView
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setContentView(binding.root)
        rvUser = binding.rvUser
        rvUser.setHasFixedSize(true)
        setTheme()

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
                viewModel.findUser(this@MainActivity, binding, rvUser, query)
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.change_theme -> {
                val i = Intent(this@MainActivity, ChangeThemeActivity::class.java)
                startActivity(i)
                return true
            }
            else -> {
                return true
            }
        }

    }

//    private fun showUser(data: ArrayList<User>) {
//        if(applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
//            rvUser.layoutManager = GridLayoutManager(this, 2)
//        } else {
//            rvUser.layoutManager = LinearLayoutManager(this)
//        }
//        val listUserAdapter = ListUserAdapter(data)
//        rvUser.adapter = listUserAdapter
//
//        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
//            override fun onItemClicked(data: User) {
//                showSelectedUser(data)
//            }
//        })
//    }
//
//
//    private fun showSelectedUser(user: User) {
//        val intentDetailUserActivity = Intent(this@MainActivity, DetailUserActivity::class.java)
//        intentDetailUserActivity.putExtra(DetailUserActivity.EXTRA_USER, user)
//        startActivity(intentDetailUserActivity)
//    }
//
//    private fun findUser(key: String){
//        val searchList = ArrayList<User>()
//        val client = ApiConfig.getApiService().getUser(key)
//        showLoading(true)
//        client.enqueue(object : Callback<FindUserResponse> {
//            override fun onResponse(
//                call: Call<FindUserResponse>,
//                response: Response<FindUserResponse>
//            ) {
//                if (response.isSuccessful) {
//                    val responseBody = response.body()
//                    if (responseBody != null) {
//                        for (i in 0 until responseBody.items!!.size){
//                            val user = User(
//                                responseBody.items[i]!!.avatarUrl,
//                                responseBody.items[i]!!.login,
//                                null,
//                                null,
//                                null,
//                                null,
//                                null,
//                                null
//                            )
//                            searchList.add(user)
//                        }
//                        showUser(searchList)
//                    }
//                    showLoading(false)
//                } else {
//                    Log.e("MainActivity", "onFailure: ${response.message()}")
//                }
//            }
//            override fun onFailure(call: Call<FindUserResponse>, t: Throwable) {
//                Log.e("MainActivity", "onFailure: ${t.message}")
//            }
//        })
//    }
//
//    private fun showLoading(isLoading: Boolean) {
//        if (isLoading) {
//            val progressBar = binding.progressBar
//            progressBar.visibility = View.VISIBLE
//        } else {
//            val progressBar = binding.progressBar
//            progressBar.visibility = View.GONE
//        }
//    }

    private fun setTheme(){
        val pref = SettingPreferences.getInstance(dataStore)
        val themeViewModel = ViewModelProvider(this, ThemeViewModelFactory(pref)).get(
            ThemeViewModel::class.java
        )
        themeViewModel.getThemeSettings().observe(this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

}