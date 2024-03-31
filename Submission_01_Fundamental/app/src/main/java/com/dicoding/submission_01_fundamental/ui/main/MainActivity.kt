package com.dicoding.submission_01_fundamental.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission_01_fundamental.R
import com.dicoding.submission_01_fundamental.databinding.ActivityMainBinding
import com.dicoding.submission_01_fundamental.ui.adapter.UserAdapter
import com.dicoding.submission_01_fundamental.ui.detailUser.DetailUserActivity
import com.dicoding.submission_01_fundamental.ui.viewModel.MainViewModel
import com.dicoding.submission_01_fundamental.ui.viewModel.ViewModelFac
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private  val viewModel by viewModels<MainViewModel>{
        ViewModelFac.getInstance(application)
    }
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "User Github"

        val layoutManager = LinearLayoutManager(this)
        binding.listUser.layoutManager = layoutManager

        binding.listUser.setHasFixedSize(true)

        viewModel.listUsers.observe(this) {
            Log.d(this@MainActivity::class.java.name, "${Gson().toJsonTree(it)}")
            adapter.submitList(it)
        }
        viewModel.isLoading.observe(this) {
            binding.progressBar.isVisible = it
        }
        viewModel.getMode().observe(this) {
                isDarkmode: Boolean ->
            if (isDarkmode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        adapter = UserAdapter {
            val intent = Intent(this, DetailUserActivity::class.java)
            intent.putExtra(DetailUserActivity.EXTRA_LOGIN, it.login)
            startActivity(intent)
        }
        binding.listUser.adapter = adapter


        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener{ _, _, _ ->
                    searchBar.setText(searchView.text)
                    viewModel.searchUser(searchView.text.toString())
                    searchView.hide()
                    false
                }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.favUser -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
            }
            R.id.settings-> {
                val intent = Intent(this, Setting::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}