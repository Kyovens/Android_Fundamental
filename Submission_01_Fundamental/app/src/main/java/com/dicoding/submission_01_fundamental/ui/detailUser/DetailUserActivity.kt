package com.dicoding.submission_01_fundamental.ui.detailUser

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.dicoding.submission_01_fundamental.R
import com.dicoding.submission_01_fundamental.data.local.UserEntity
import com.dicoding.submission_01_fundamental.databinding.ActivityDetailUserBinding
import com.dicoding.submission_01_fundamental.ui.adapter.SectionAdapter
import com.dicoding.submission_01_fundamental.ui.viewModel.DetailViewModel
import com.dicoding.submission_01_fundamental.ui.viewModel.ViewModelFac
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val detailViewModel by viewModels<DetailViewModel>{
        ViewModelFac.getInstance(application)
    }
    private var userEntity: UserEntity = UserEntity(0, null, null)


    companion object{
        const val  EXTRA_LOGIN = "extra_login"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Detail User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val username = intent.getStringExtra(EXTRA_LOGIN)

        detailViewModel.isLoading.observe(this) {
            binding.progressBar.isVisible = it
        }
        detailViewModel.getDetailUser(username.toString())
        detailViewModel.userEntity.observe(this) {
            userEntity = it
        }
        detailViewModel.detailUser.observe(this) {
            with(binding) {
                if (it!=null) {
                    follower.text = it.followers.toString()
                    following.text = it.following.toString()
                    fullName.text = it.name
                    userName.text = it.login
                    Glide.with(binding.root)
                        .load(it.avatarUrl)
                        .into(binding.photoProfile)
                        .clearOnDetach()
                }
            }
        }
        val sectionAdapter = SectionAdapter(this)
        val viewPage = binding.viewPager
        viewPage.adapter = sectionAdapter
        val tabs = binding.tabLayout
        TabLayoutMediator(tabs, viewPage) {tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        detailViewModel.isFav(username.toString()).observe(this) {
            if(it) {
                binding.btnFav.setImageDrawable(ContextCompat.getDrawable(binding.btnFav.context, R.drawable.favorite))
                binding.btnFav.setOnClickListener {
                    detailViewModel.addFav(userEntity)
                }
            }
        }
    }


}