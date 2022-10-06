package com.saifer.githubusers.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saifer.githubusers.adapter.ListUserAdapter
import com.saifer.githubusers.User
import com.saifer.githubusers.api.ApiConfig
import com.saifer.githubusers.databinding.FragmentDetailUserFollowerBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailUserFollowerFragment : Fragment() {

    
    private lateinit var rvFollower: RecyclerView
    private lateinit var binding: FragmentDetailUserFollowerBinding
    private lateinit var adapter: ListUserAdapter
    private lateinit var  progressBar: ProgressBar

    private val followersList = ArrayList<User>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentDetailUserFollowerBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//         Inflate the layout for this fragment
        rvFollower = binding.rvFollowerDtlUser
        progressBar = binding.progressBar
        rvFollower.layoutManager = LinearLayoutManager(activity)
        rvFollower.adapter = ListUserAdapter(followersList)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showFollowers(requireArguments().getString("uname")!!)

        val layoutManager = LinearLayoutManager(context)
        rvFollower = binding.rvFollowerDtlUser
        rvFollower.layoutManager = layoutManager
        rvFollower.setHasFixedSize(true)
        adapter = ListUserAdapter(followersList)
    }


    private fun showFollowers(key: String){
        showLoading(true)
        followersList.clear()
        val client = ApiConfig.getApiService().getUserFollower(key)
        client.enqueue(object : Callback<List<FollowerUserResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowerUserResponseItem>>,
                response: Response<List<FollowerUserResponseItem>>
            ){
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        for (i in responseBody.indices){
                            val follower = User(
                                responseBody[i].avatarUrl,
                                responseBody[i].login,
                                responseBody[i].id,
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
                val listUserAdapter = ListUserAdapter(followersList)
                rvFollower.adapter = listUserAdapter
                listUserAdapter.setOnItemClickCallback(object :
                    ListUserAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: User) {}
                })
                showLoading(false)
            }
            override fun onFailure(call: Call<List<FollowerUserResponseItem>>, t: Throwable) {
                Log.e("Detail User Fragment", "onFailure: ${t.message}")
            }
        })
    }

    fun launchFragment(uname: String?): DetailUserFollowerFragment {
        val fragment = DetailUserFollowerFragment()

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