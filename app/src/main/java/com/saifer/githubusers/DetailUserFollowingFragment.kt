package com.saifer.githubusers

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saifer.githubusers.databinding.FragmentDetailUserFollowerBinding
import com.saifer.githubusers.databinding.FragmentDetailUserFollowingBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailUserFollowingFragment(private val uname: String?) : Fragment() {

    private lateinit var rvFollowing: RecyclerView
    private lateinit var binding: FragmentDetailUserFollowingBinding
    private lateinit var adapter: ListUserAdapter

    private val followingList = ArrayList<User>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentDetailUserFollowingBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        val view = inflater.inflate(R.layout.fragment_detail_user_follower, container, false)
        rvFollowing = binding.rvFollowingDtlUser
        rvFollowing.layoutManager = LinearLayoutManager(activity)
        rvFollowing.adapter = ListUserAdapter(followingList)
        return rvFollowing
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showFollowers(uname!!)

        val layoutManager = LinearLayoutManager(context)
        rvFollowing = binding.rvFollowingDtlUser
        rvFollowing.layoutManager = layoutManager
        rvFollowing.setHasFixedSize(true)
        adapter = ListUserAdapter(followingList)
    }


    private fun showFollowers(key: String){
        val client = ApiConfig.getApiService().getUserFollowing(key)
        client.enqueue(object : Callback<List<FollowingUserResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowingUserResponseItem>>,
                response: Response<List<FollowingUserResponseItem>>
            ){
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        for (i in responseBody.indices){
                            val follower = User(
                                responseBody[i].avatarUrl,
                                responseBody[i].login,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null
                            )
                            followingList.add(follower)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<List<FollowingUserResponseItem>>, t: Throwable) {
                Log.e("Detail User Fragment", "onFailure: ${t.message}")
            }
        })
    }
}