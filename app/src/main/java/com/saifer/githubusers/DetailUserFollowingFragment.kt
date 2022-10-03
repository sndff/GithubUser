package com.saifer.githubusers

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saifer.githubusers.api.ApiConfig
import com.saifer.githubusers.databinding.FragmentDetailUserFollowingBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailUserFollowingFragment(private val uname: String?) : Fragment() {

    private lateinit var rvFollowing: RecyclerView
    private lateinit var binding: FragmentDetailUserFollowingBinding
    private lateinit var adapter: ListUserAdapter

    private lateinit var  progBar: ProgressBar
    private val followingList = ArrayList<User>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentDetailUserFollowingBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//         Inflate the layout for this fragment
        rvFollowing = binding.rvFollowingDtlUser
        progBar = binding.progressBar
        rvFollowing.layoutManager = LinearLayoutManager(activity)
        rvFollowing.adapter = ListUserAdapter(followingList)
        return binding.root
//        return binding.progressBar
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showFollowing(uname!!)

        val layoutManager = LinearLayoutManager(context)
        rvFollowing = binding.rvFollowingDtlUser
        rvFollowing.layoutManager = layoutManager
        rvFollowing.setHasFixedSize(true)
        adapter = ListUserAdapter(followingList)
    }


    private fun showFollowing(key: String){
        showLoading(true)
        followingList.clear()
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
                val listUserAdapter = ListUserAdapter(followingList)
                rvFollowing.adapter = listUserAdapter
                listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: User){/*do nothing*/}
                })
                showLoading(false)
            }
            override fun onFailure(call: Call<List<FollowingUserResponseItem>>, t: Throwable) {
                Log.e("Detail User Fragment", "onFailure: ${t.message}")
            }
        })
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            progBar.visibility = View.VISIBLE
        } else {
            progBar.visibility = View.INVISIBLE
        }
    }
}