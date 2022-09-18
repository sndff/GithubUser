package com.saifer.githubusers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.saifer.githubusers.databinding.ActivityDetailUserBinding

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)
        title = "Detail User"

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User

        Glide.with(binding.imgDtlAvatar)
            .load(user.avatar)
            .circleCrop()
            .into(binding.imgDtlAvatar)
        binding.textDtlName.text = user.name
        binding.textDtlUsername.text = user.username
        binding.textDtlValFollower.text = user.followers
        binding.textDtlValFollowing.text = user.following
        binding.textDtlValRepo.text = user.repo
        binding.textDtlValLocation.text = user.location
        binding.textDtlValCompany.text = user.company

    }


    companion object{
        var EXTRA_USER = ""
    }
}

