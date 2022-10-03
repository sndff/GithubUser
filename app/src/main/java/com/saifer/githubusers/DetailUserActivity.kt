package com.saifer.githubusers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.saifer.githubusers.api.ApiConfig
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
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
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
                        showLoading(false)
                    }
                } else {
                    Log.e("MainActivity", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                Log.e("MainActivity", "onFailure: ${t.message}")
            }
        })

        // Tab Layout
        val pageAdapter = DetailUserPagerAdapter(this, user.username)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = pageAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            val progBar = binding.progressBar
            progBar.visibility = View.VISIBLE
        } else {
            val progBar = binding.progressBar
            progBar.visibility = View.GONE
        }
    }
    companion object{
        var EXTRA_USER = ""
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}

