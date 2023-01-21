package com.example.masir.ui.main.feachers.user.details.follow_lists

import android.os.Bundle
import android.view.View
import com.example.masir.R
import com.example.masir.databinding.FragmentFollowerFollowingBinding
import com.example.masir.model.User
import com.example.masir.ui.main.feachers.user.UsersVM
import com.example.masir.ui.main.feachers.user.main_screen.UsersAdapter
import com.example.masir.utility.BaseFragmentByVM
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowListFragment : BaseFragmentByVM<FragmentFollowerFollowingBinding, UsersVM>(),
    UsersAdapter.OnUserCallBacks {
    override val layoutId: Int get() = R.layout.fragment_follower_following
    override val viewModel: UsersVM by viewModel()

    private lateinit var adapter : UsersAdapter

    fun newInstance(users: ArrayList<User>, page: Int): FollowListFragment {
        val args = Bundle()
        args.putSerializable("data",users)
        args.putInt("page",page)
        val fragment = FollowListFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = UsersAdapter(this)
        binding.recyclerView.adapter = adapter

        val users = requireArguments().getSerializable("data") as ArrayList<User>
        val page = requireArguments().getInt("page",1)
        adapter.setItemByDiffUtil(users)

    }

    override fun onItemClick(model: User) { /* your code */ }

}