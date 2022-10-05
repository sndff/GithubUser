package com.saifer.githubusers.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saifer.githubusers.adapter.ListUserAdapter
import com.saifer.githubusers.User
import com.saifer.githubusers.api.ApiConfig
import com.saifer.githubusers.databinding.FragmentDetailUserFollowingBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailUserFollowingFragment : Fragment() {

    private lateinit var rvFollowing: RecyclerView
    private lateinit var binding: FragmentDetailUserFollowingBinding
    private lateinit var adapter: ListUserAdapter

    private lateinit var  progressBar: ProgressBar
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
        progressBar = binding.progressBar
        rvFollowing.layoutManager = LinearLayoutManager(activity)
        rvFollowing.adapter = ListUserAdapter(followingList)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showFollowing(requireArguments().getString("uname")!!)

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
                listUserAdapter.setOnItemClickCallback(object :
                    ListUserAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: User){/*do nothing*/}
                })
                showLoading(false)
            }
            override fun onFailure(call: Call<List<FollowingUserResponseItem>>, t: Throwable) {
                Log.e("Detail User Fragment", "onFailure: ${t.message}")
            }
        })
    }

    fun launchFragment(uname: String?): DetailUserFollowingFragment {
        val fragment = DetailUserFollowingFragment()

        val bundle = Bundle().apply {
            putString("uname", uname)
        }

        fragment.arguments = bundle
        return fragment
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.INVISIBLE
        }
    }
}