package com.dicoding.submission_01_fundamental.ui.detailUser

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission_01_fundamental.databinding.FragmentFollowActivityBinding
import com.dicoding.submission_01_fundamental.ui.adapter.UserAdapter
import com.dicoding.submission_01_fundamental.ui.viewModel.FollowViewModel



class FollowActivity : Fragment() {
    private lateinit var binding: FragmentFollowActivityBinding
    private lateinit var adapter: UserAdapter
    private val followViewModel by viewModels<FollowViewModel>()


    companion object {
        const val SECTION_NUMBER = "section_number"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowActivityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val section = arguments?.getInt(SECTION_NUMBER, 0)
        val username = requireActivity().intent.getStringExtra(DetailUserActivity.EXTRA_LOGIN)

        val layoutManager = LinearLayoutManager(context)
        binding.listUser.layoutManager = layoutManager

        followViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }

        adapter = UserAdapter{
            val intent = Intent(requireActivity(), DetailUserActivity::class.java)
            intent.putExtra(DetailUserActivity.EXTRA_LOGIN, it.login)
            startActivity(intent)
        }

        if (section == 1) {
            followViewModel.getFollowers(username.toString())
            followViewModel.listFollower.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
        } else {
            followViewModel.getFollowing(username.toString())
            followViewModel.listFollowing.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }
}