package com.saifer.githubusers.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.saifer.githubusers.R
import com.saifer.githubusers.User
import com.saifer.githubusers.databinding.ActivityDetailUserBinding
import com.saifer.githubusers.favorite.helper.ViewModelFactory
import com.saifer.githubusers.theme.ChangeThemeActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)
        title = "Detail User"

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = obtainViewModel(this)

        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User

        viewModel.setDetailUser(binding, user)
        viewModel.setTabLayout(this, binding, user)

        var _checked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(user.id)
            withContext(Dispatchers.Main){
                if (count != null) if(count > 0){
                    binding.btnFav.isChecked = true
                    _checked = true
                }
                else {
                    binding.btnFav.isChecked = false
                    _checked = false
                }
            }
        }

        binding.btnFav.setOnClickListener{
            _checked = !_checked
            if (_checked){
                viewModel.addToFavorite(user.id, user.username!!, user.avatar!!)
                Toast.makeText(this@DetailUserActivity, "Followed ${user.username}", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.deleteFromFavorite(user.id)
                Toast.makeText(this@DetailUserActivity, "Unfollowed ${user.username}", Toast.LENGTH_SHORT).show()
            }
            binding.btnFav.isChecked = _checked
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailUserViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DetailUserViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.detail_option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.btn_change_theme -> {
                val i = Intent(this@DetailUserActivity, ChangeThemeActivity::class.java)
                startActivity(i)
                return true
            }
            else -> {
                return true
            }
        }

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

