package com.saifer.githubusers.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.saifer.githubusers.R
import com.saifer.githubusers.User
import com.saifer.githubusers.adapter.DetailUserPagerAdapter
import com.saifer.githubusers.databinding.ActivityDetailUserBinding

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)
        title = "Detail User"

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)

        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User

        viewModel.setDetailUser(binding, user)
        viewModel.setTabLayout(this, binding, user)

    }

    companion object{
        var EXTRA_USER = ""
        @StringRes
        internal val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}

