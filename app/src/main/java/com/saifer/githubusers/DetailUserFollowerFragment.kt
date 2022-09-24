package com.saifer.githubusers

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saifer.githubusers.databinding.FragmentDetailUserFollowerBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailUserFollowerFragment(private val uname: String?) : Fragment() {

    private lateinit var rvFollower: RecyclerView
    private lateinit var binding: FragmentDetailUserFollowerBinding
    val followersList = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentDetailUserFollowerBinding.inflate(layoutInflater)
        rvFollower = binding.rvFollowerDtlUser


        // parsing JSON
        val client = ApiConfig.getApiService().getUserFollower(uname!!)
        client.enqueue(object : Callback<FollowerUserResponse> {
            override fun onResponse(
                call: Call<FollowerUserResponse>,
                response: Response<FollowerUserResponse>
            ){
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        for (i in 0 until responseBody.followerUserResponse!!.size){
                            val follower = User(
                                responseBody.followerUserResponse[i]!!.avatarUrl,
                                responseBody.followerUserResponse[i]!!.login,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null
                            )
                            followersList.add(follower)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<FollowerUserResponse>, t: Throwable) {
                Log.e("MainActivity", "onFailure: ${t.message}")

            }
        })
        val follower = User(
            null,
            "Sandi Faisal Ferdiansyah",
            null,
            null,
            null,
            null,
            null,
            null
        )
        followersList.add(follower)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_user_follower, container, false)
        showUser(followersList)
    }

    private fun showUser(data: ArrayList<User>) {
        val listUserAdapter = ListUserAdapter(data)
        rvFollower.adapter = listUserAdapter

    }

}