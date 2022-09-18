package com.saifer.githubusers

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        showDetailUser()

    }

    private fun showDetailUser() {
        if(applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            rvUser.layoutManager = GridLayoutManager(this, 2)
        } else {
            rvUser.layoutManager = LinearLayoutManager(this)
        }
        val listUserAdapter = ListUserAdapter(list)
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