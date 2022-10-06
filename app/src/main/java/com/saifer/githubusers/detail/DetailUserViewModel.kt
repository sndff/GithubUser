package com.saifer.githubusers.detail

import android.app.Application
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.saifer.githubusers.User
import com.saifer.githubusers.adapter.DetailUserPagerAdapter
import com.saifer.githubusers.api.ApiConfig
import com.saifer.githubusers.databinding.ActivityDetailUserBinding
import com.saifer.githubusers.favorite.database.Favorite
import com.saifer.githubusers.favorite.repository.FavoriteRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : ViewModel() {

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun setDetailUser(binding: ActivityDetailUserBinding, user: User){
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
                        showLoading(binding, false)
                    }
                } else {
                    Log.e("DetailUserViewModel", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                Log.e("DetailUserViewModel", "onFailure: ${t.message}")
            }
        })
    }

    fun setTabLayout(activity: AppCompatActivity, binding: ActivityDetailUserBinding, user: User){
        val pageAdapter = DetailUserPagerAdapter(activity, user.username)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = pageAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = activity.resources.getString(DetailUserActivity.TAB_TITLES[position])
        }.attach()
        activity.supportActionBar?.elevation = 0f
    }

    private fun showLoading(binding: ActivityDetailUserBinding, isLoading: Boolean) {
        if (isLoading) {
            val progressBar = binding.progressBar
            progressBar.visibility = View.VISIBLE
        } else {
            val progressBar = binding.progressBar
            progressBar.visibility = View.GONE
        }
    }

    fun addToFavorite(id: Int, username: String, avatar: String){
        val user = Favorite(
            id,
            username,
            avatar
        )
        mFavoriteRepository.insert(user)
    }

    fun checkUser(id: Int) = mFavoriteRepository.checkUser(id)

    fun deleteFromFavorite(id: Int){
        mFavoriteRepository.delete(id)
    }


}