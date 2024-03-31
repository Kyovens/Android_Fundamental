package com.dicoding.submission_01_fundamental.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission_01_fundamental.databinding.ActivityFavoriteBinding
import com.dicoding.submission_01_fundamental.ui.adapter.FavAdapter
import com.dicoding.submission_01_fundamental.ui.detailUser.DetailUserActivity
import com.dicoding.submission_01_fundamental.ui.viewModel.FavViewModel
import com.dicoding.submission_01_fundamental.ui.viewModel.ViewModelFac

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FavAdapter
    private val favViewModel: FavViewModel by viewModels {
        ViewModelFac.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Favorite User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val layoutManager = LinearLayoutManager(this)
        binding.listFav.layoutManager = layoutManager
        adapter = FavAdapter({
            val intent = Intent(this, DetailUserActivity::class.java)
            intent.putExtra(DetailUserActivity.EXTRA_LOGIN, it.username)
            startActivity(intent)
        }, {
            favViewModel.deleteFav(it.username.toString())
            favViewModel.getAllFav().observe(this){res->
                adapter.submitList(res)
            }
        })
        binding.listFav.adapter = adapter
        favViewModel.getAllFav().observe(this){res ->
            binding.progressBar.isGone = true
            adapter.submitList(res)
        }
    }
}