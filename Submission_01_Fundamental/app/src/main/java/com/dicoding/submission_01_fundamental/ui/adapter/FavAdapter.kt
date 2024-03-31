package com.dicoding.submission_01_fundamental.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submission_01_fundamental.data.local.UserEntity
import com.dicoding.submission_01_fundamental.databinding.ItemUserGithubBinding


class FavAdapter(private val onClick: (UserEntity) -> Unit, private val onDelete: (UserEntity) -> Unit) : ListAdapter<UserEntity, FavAdapter.MyFavViewHolder>(DIFF_CALLBACK){

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserEntity>() {
            override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    class MyFavViewHolder(val binding: ItemUserGithubBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserEntity){
            binding.username.text = user.username
            binding.deleteFav.isVisible = true
            Glide.with(binding.root)
                .load(user.avatar)
                .into(binding.photoProfile)
                .clearOnDetach()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFavViewHolder {
        val binding = ItemUserGithubBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyFavViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyFavViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
        holder.itemView.setOnClickListener { onDelete(user) }
        holder.binding.deleteFav.setOnClickListener{
            onDelete(user)
        }
    }
}