package com.example.masir.ui.main.feachers.user.details.follow_lists

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.example.masir.R
import com.example.masir.databinding.FragmentFollowerFollowingBinding
import com.example.masir.model.User
import com.example.masir.ui.main.feachers.user.SharedUserVM
import com.example.masir.ui.main.feachers.user.main_screen.UsersAdapter
import com.example.masir.utility.BaseFragmentByVM
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowListFragment : BaseFragmentByVM<FragmentFollowerFollowingBinding, SharedUserVM>(),
    UsersAdapter.OnUserCallBacks {
    override val layoutId: Int get() = R.layout.fragment_follower_following
    override val viewModel: SharedUserVM by viewModel()

    private lateinit var adapter : UsersAdapter

    companion object{
        fun newInstance(model : ArrayList<User>): FollowListFragment {
            val args = Bundle()
            args.putSerializable("data",model)
            val fragment = FollowListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = UsersAdapter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter
        val users = requireArguments().getSerializable("data") as ArrayList<User>
        adapter.setItemByDiffUtil(users)
    }


    override fun viewClickEvents() {

    }

    override fun registerObservers() {
        super.registerObservers()
    }

    override fun onItemClick(model: User) {

    }


}