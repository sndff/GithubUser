package com.saifer.githubusers.favorite

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saifer.githubusers.R
import com.saifer.githubusers.User
import com.saifer.githubusers.adapter.ListUserAdapter
import com.saifer.githubusers.databinding.ActivityFavoriteBinding
import com.saifer.githubusers.detail.DetailUserActivity
import com.saifer.githubusers.favorite.database.Favorite
import com.saifer.githubusers.favorite.helper.ViewModelFactory
import com.saifer.githubusers.theme.ChangeThemeActivity

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var rvFav: RecyclerView
    private lateinit var adapter: ListUserAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        val viewModel = obtainViewModel(this@FavoriteActivity)
        rvFav = binding.rvFavorite
        rvFav.layoutManager = LinearLayoutManager(this)
        rvFav.setHasFixedSize(true)
        title = "Favorite Users"

        viewModel.getAllFavorites().observe(this) {
            if (it != null) {
                val list = mapList(it)
                adapter = ListUserAdapter(list)
                adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: User) {
                        showSelectedUser(this@FavoriteActivity, data)
                    }
                })
                rvFav.adapter = adapter
            }
        }

        setContentView(binding.root)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.favorite_option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.btn_change_theme -> {
                val i = Intent(this@FavoriteActivity, ChangeThemeActivity::class.java)
                startActivity(i)
                return true
            }
            else -> {
                return true
            }
        }
    }

    private fun mapList(users: List<Favorite>): ArrayList<User> {
        val listUser = ArrayList<User>()
        for(user in users){
            val userMapped = User(
                user.avatar,
                user.login,
                user.id,
                null,
                null,
                null,
                null,
                null,
                null
            )
            listUser.add(userMapped)
        }
        return listUser
    }

    fun showSelectedUser(activity: AppCompatActivity, user: User) {
        val intentDetailUserActivity = Intent(this@FavoriteActivity, DetailUserActivity::class.java)
        intentDetailUserActivity.putExtra(DetailUserActivity.EXTRA_USER, user)
        activity.startActivity(intentDetailUserActivity)
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }
}