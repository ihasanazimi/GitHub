package com.example.masir.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.masir.R
import com.example.masir.databinding.LoadingBarBinding
import com.example.masir.utility.BaseFragment
import com.example.masir.utility.extentions.onBackClick

class LoadingFragment : BaseFragment<LoadingBarBinding>() {
    override val layoutId: Int get() = R.layout.loading_bar
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.onBackClick { findNavController().navigateUp() }
    }
}