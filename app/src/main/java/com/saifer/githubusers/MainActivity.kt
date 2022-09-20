package com.saifer.githubusers

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saifer.githubusers.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var rvUser: RecyclerView
    private val list = ArrayList<User>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvUser = findViewById(R.id.rv_user)
        rvUser.setHasFixedSize(true)

        list.addAll(listUsers)
        showDetailUser(list)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // show similar list users
                val searchList = ArrayList<User>()
                var isEmpty = true
                for (i in 0 until list.size){
                    if (list[i].username.contains(query.toString())){
                        searchList.add(list[i])
                        isEmpty = false
                        Toast.makeText(this@MainActivity, "Users Found", Toast.LENGTH_SHORT).show()
                    } else if(!list[i].username.contains(query.toString()) && i == list.size -1 && isEmpty){
                        Toast.makeText(this@MainActivity, "Users not Found", Toast.LENGTH_SHORT).show()
                    } else {
                        continue
                    }
                }
                showDetailUser(searchList)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val searchList = ArrayList<User>()
                for (i in 0 until list.size){
                    if (list[i].username.contains(newText.toString())){
                        searchList.add(list[i])
                    } else {
                        continue
                    }
                }
                showDetailUser(searchList)
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

    private val listUsers: ArrayList<User>
    get() {
        val dataAvatar = resources.obtainTypedArray(R.array.avatar)
        val dataName = resources.getStringArray(R.array.name)
        val dataUsername = resources.getStringArray(R.array.username)
        val dataFollower = resources.getStringArray(R.array.followers)
        val dataFollowing = resources.getStringArray(R.array.following)
        val dataLocation = resources.getStringArray(R.array.location)
        val dataRepository = resources.getStringArray(R.array.repository)
        val dataCompany = resources.getStringArray(R.array.company)
        val listUser = ArrayList<User>()
        for (i in dataUsername.indices){
            val user = User(dataAvatar.getResourceId(i,-1), dataName[i], dataUsername[i], dataFollower[i], dataFollowing[i], dataRepository[i], dataLocation[i], dataCompany[i])
            listUser.add(user)
        }
        return listUser
    }
}