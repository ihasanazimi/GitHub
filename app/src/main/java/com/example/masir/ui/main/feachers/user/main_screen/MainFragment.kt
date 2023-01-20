package com.example.masir.ui.main.feachers.user.main_screen

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import com.example.masir.R
import com.example.masir.databinding.FragmentMainBinding
import com.example.masir.model.User
import com.example.masir.ui.main.feachers.user.SharedUserVM
import com.example.masir.ui.main.feachers.user.details.follow_lists.UserDetailsFragment
import com.example.masir.utility.BaseFragmentByVM
import com.example.masir.utility.extentions.hide
import com.example.masir.utility.extentions.onBackClick
import com.example.masir.utility.extentions.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragmentByVM<FragmentMainBinding, SharedUserVM>(), UsersAdapter.OnUserCallBacks {
    override val layoutId: Int get() = R.layout.fragment_main
    override val viewModel: SharedUserVM by viewModel()

    private lateinit var adapter : UsersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewConfig()
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) viewModel.searchUser(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.clearList()
                return false
            }
        })
        this.onBackClick { mainHelper.finish() }
    }

    private fun recyclerViewConfig() {
        adapter = UsersAdapter(this)
        binding.recyclerView.adapter = adapter
    }

    override fun viewClickEvents() {

        binding.searchView.setOnCloseListener {
            adapter.clearList()
            binding.notFoundImage.show()
            viewModel.stopLoading()
            return@setOnCloseListener false
        }

        binding.btnFavorites.setOnClickListener{ findNavController().navigate(R.id.action_mainFragment_to_favoritesListFragment) }
    }

    override fun registerObservers() {
        super.registerObservers()

        viewModel.foundUsers.observe(viewLifecycleOwner){
            adapter.setItemByDiffUtil(it.items)
            if (it.items.isEmpty()) binding.notFoundImage.show()
            else binding.notFoundImage.hide()
        }

        viewModel.showProgress.observe(viewLifecycleOwner){
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            if (it) binding.notFoundImage.hide()
        }

    }

    override fun onItemClick(model: User) {
        val bundle = Bundle()
        bundle.putString(UserDetailsFragment.LOGIN_KEY,model.login)
        findNavController().navigate(R.id.action_mainFragment_to_userDetailsFragment,bundle)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.stopLoading()
    }


}