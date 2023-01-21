package com.example.masir.ui.main.feachers.user.details.follow_lists

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.masir.R
import com.example.masir.databinding.FragmentFollowerFollowingBinding
import com.example.masir.model.User
import com.example.masir.ui.main.feachers.user.main_screen.UsersAdapter
import com.example.masir.utility.BaseFragmentByVM
import com.example.masir.utility.EndlessRecyclerViewScrollListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowingFragment : BaseFragmentByVM<FragmentFollowerFollowingBinding, FollowVM>(), UsersAdapter.OnUserCallBacks {
    override val layoutId: Int get() = R.layout.fragment_follower_following
    override val viewModel: FollowVM by viewModel()

    private lateinit var adapter : UsersAdapter
    private lateinit var endlessListener: EndlessRecyclerViewScrollListener

    var users : ArrayList<User> = arrayListOf()
    var page = -1
    var userName = ""

    fun newInstance(users: ArrayList<User>, page: Int , userName : String): FollowingFragment {
        val args = Bundle()
        args.putString("login",userName)
        args.putSerializable("data",users)
        args.putInt("page",page)
        val fragment = FollowingFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = UsersAdapter(this)
        binding.recyclerView.adapter = adapter

        users = requireArguments().getSerializable("data") as ArrayList<User>
        page = requireArguments().getInt("page",1)
        userName = requireArguments().getString("login","")
        viewModel.putFollowingInLiveData(users)

        endlessListener = object : EndlessRecyclerViewScrollListener(binding.recyclerView.layoutManager!!) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    viewModel.pageNumber++
                    viewModel.getAllFollowingOfUser(userName)
                }
            }
        binding.recyclerView.addOnScrollListener(endlessListener)

    }


    override fun registerObservers() {
        super.registerObservers()
        viewModel.following.observe(viewLifecycleOwner){ adapter.setItemByDiffUtil(it) }
        viewModel.showProgress.observe(viewLifecycleOwner){ binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE }
    }

    override fun onItemClick(model: User) { /* your code */ }

}