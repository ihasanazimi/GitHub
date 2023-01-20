package com.example.masir.ui.main.feachers.user.details.follow_lists

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.masir.R
import com.example.masir.databinding.FragmentUserDetailsBinding
import com.example.masir.model.SingleUserObj
import com.example.masir.ui.main.feachers.user.SharedUserVM
import com.example.masir.utility.BaseFragmentByVM
import com.example.masir.utility.ToggleImageView
import com.example.masir.utility.extentions.onBackClick
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserDetailsFragment : BaseFragmentByVM<FragmentUserDetailsBinding, SharedUserVM>(){
    override val layoutId: Int get() = R.layout.fragment_user_details
    override val viewModel: SharedUserVM by viewModel()

    companion object{
        const val LOGIN_KEY = "login_KEY"
    }

    private lateinit var adapter: ViewPagerAdapter
    private var targetUser : SingleUserObj ? = null
    private var login : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        login = requireArguments().getString(LOGIN_KEY,"")
        lifecycleScope.launch { viewModel.getUserDetails(login) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ViewPagerAdapter(requireActivity().supportFragmentManager,this.lifecycle)
        binding.viewPager.adapter = adapter
        likeUiChange()
        this.onBackClick { findNavController().navigateUp() }
    }

    private fun likeUiChange() {
        val checkState = viewModel.isSaved(login)
        if (checkState) binding.toggleFavorites.setChecked()
        else binding.toggleFavorites.setUnchecked()
    }


    override fun viewClickEvents() {

        binding.toggleFavorites.addStateListener(object : ToggleImageView.OnStateChangedListener{
            override fun onChecked() {
                if (targetUser != null)
                    viewModel.addFavorite(targetUser!!)
            }

            override fun onUnchecked() {
                if (targetUser != null)
                    viewModel.removeFavorite(targetUser!!)
                viewModel.removeFavoriteOnLiveData(targetUser!!)
            }
        })


        binding.btnBack.setOnClickListener { findNavController().navigateUp() }

    }

    override fun registerObservers() {
        super.registerObservers()

        viewModel.targetUserFollowers.observe(viewLifecycleOwner){
//            adapter.addItems(it as ArrayList<User>)
        }

        viewModel.targetUserFollowing.observe(viewLifecycleOwner){
//            adapter.addItems(it as ArrayList<User>)
        }

        viewModel.singleUser.observe(viewLifecycleOwner){
            if (it != null) {

                binding.tvUserName.apply {
                    text ="Username : ${it.login}"
                    visibility = View.VISIBLE
                }

                if (!it.name.isNullOrEmpty()){
                    binding.tvUserFullName.apply {
                        text = it.name
                        visibility = View.VISIBLE
                    }
                }

                if (!it.bio.isNullOrEmpty()) {
                    binding.tvBio.apply {
                        text = "Bio : ${it.bio}"
                        visibility = View.VISIBLE
                    }
                }

                if (!it.location.isNullOrEmpty()) {
                    binding.tvLocation.apply {
                        text = "${it.location}"
                        visibility = View.VISIBLE
                    }
                }

                binding.followingCountTv.text = it.following.toString()
                binding.followerCountTv.text = it.followers.toString()
                binding.repositoryCountTv.text = it.public_repos.toString()

                Glide.with(binding.root.context)
                    .load(it.avatar_url)
                    .timeout(30000)
                    .error(R.drawable.ic_outline_info_24)
                    .placeholder(R.drawable.ic_place_holder)
                    .into(binding.ivUserCover)

                // save it on global variable
                targetUser = it
            }
        }

        viewModel.showProgress.observe(viewLifecycleOwner){
            if (it) showLoading(R.id.rootContainer) else hideLoading()
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner){
            if (it.isNotEmpty()) findNavController().navigateUp()
        }

    }




}