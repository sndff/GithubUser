package com.saifer.githubusers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.saifer.githubusers.databinding.ActivityDetailUserBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)
        title = "Detail User"

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User

        val client = ApiConfig.getApiService().getDetailUser(user.username!!)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
//                        showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
//                        Toast.makeText(this@DetailUserActivity, responseBody.company, Toast.LENGTH_SHORT).show()
                        Glide.with(binding.imgDtlAvatar)
                            .load(responseBody.avatarUrl)
                            .circleCrop()
                            .into(binding.imgDtlAvatar)
                        binding.textDtlName.text = responseBody.name
                        binding.textDtlUsername.text = responseBody.login
                        binding.textDtlValFollower.text = responseBody.followers.toString()
                        binding.textDtlValFollowing.text = responseBody.following.toString()
                        binding.textDtlValRepo.text = responseBody.publicRepos.toString()
                        binding.textDtlValLocation.text = responseBody.location
                        binding.textDtlValCompany.text = responseBody.company
                    }
                } else {
                    Log.e("MainActivity", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
//                        showLoading(false)
                Log.e("MainActivity", "onFailure: ${t.message}")
            }
        })


    }

    companion object{
        var EXTRA_USER = ""
    }
}

