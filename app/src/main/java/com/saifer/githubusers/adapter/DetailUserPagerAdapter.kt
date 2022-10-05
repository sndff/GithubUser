package com.saifer.githubusers.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.saifer.githubusers.detail.DetailUserFollowerFragment
import com.saifer.githubusers.detail.DetailUserFollowingFragment

class DetailUserPagerAdapter(activity: AppCompatActivity, private val uname: String?): FragmentStateAdapter(activity) {
    private val follower = DetailUserFollowerFragment()
    private val following = DetailUserFollowingFragment()

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = follower.launchFragment(uname)
            1 -> fragment = following.launchFragment(uname)
        }
        return fragment as Fragment
    }
}