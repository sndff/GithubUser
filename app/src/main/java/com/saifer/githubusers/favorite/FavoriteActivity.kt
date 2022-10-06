package com.saifer.githubusers.favorite

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.saifer.githubusers.R
import com.saifer.githubusers.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var rvFav: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        rvFav = binding.rvFavorite
        rvFav.setHasFixedSize(true)


    }
}