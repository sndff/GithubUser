package com.saifer.githubusers

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.saifer.githubusers.databinding.FragmentDetailUserFollowerBinding
import com.saifer.githubusers.databinding.FragmentDetailUserFollowingBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailUserFollowingFragment(private val uname: String?) : Fragment() {

    private lateinit var rvFollowing: RecyclerView
    private lateinit var binding: FragmentDetailUserFollowingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentDetailUserFollowingBinding.inflate(layoutInflater)
        rvFollowing = binding.rvFollowingDtlUser

        val followingList = ArrayList<User>()
        // parsing JSON

        val listUserAdapter = ListUserAdapter(followingList)
        rvFollowing.adapter = listUserAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_user_following, container, false)
    }
}