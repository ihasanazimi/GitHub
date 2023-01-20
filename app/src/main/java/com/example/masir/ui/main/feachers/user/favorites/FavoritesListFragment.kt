package com.example.masir.ui.main.feachers.user.favorites

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.masir.R
import com.example.masir.databinding.FragmentFavoritesListBinding
import com.example.masir.model.SingleUserObj
import com.example.masir.ui.main.feachers.user.SharedUserVM
import com.example.masir.ui.main.feachers.user.details.follow_lists.UserDetailsFragment
import com.example.masir.utility.BaseFragmentByVM
import com.example.masir.utility.extentions.onBackClick
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesListFragment : BaseFragmentByVM<FragmentFavoritesListBinding, SharedUserVM>(),
    FavoriteUsersAdapter.OnUserCallBacks {
    override val layoutId: Int get() = R.layout.fragment_favorites_list
    override val viewModel: SharedUserVM by viewModel()

    private lateinit var adapter : FavoriteUsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = FavoriteUsersAdapter(this)
        viewModel.getAllFavoritesList(0)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter
        this.onBackClick { findNavController().navigateUp() }
    }

    override fun registerObservers() {
        super.registerObservers()
        viewModel.favoritesList.observe(viewLifecycleOwner){ adapter.setItemByDiffUtil(it) }
    }

    override fun onItemClick(model: SingleUserObj) {
        val bundle = Bundle()
        bundle.putString(UserDetailsFragment.LOGIN_KEY,model.login)
        findNavController().navigate(R.id.action_favoritesListFragment_to_userDetailsFragment,bundle)
    }


}