package com.example.masir.utility

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.masir.ui.LoadingFragment
import com.example.masir.utility.extentions.showToast

abstract class BaseFragmentByVM<V : ViewDataBinding , VM : BaseViewModel> : Fragment() {

    abstract val viewModel: VM
    private var _binding: V? = null
    val binding get() = _binding!!
    @get:LayoutRes
    abstract val layoutId: Int
    val mainHelper by lazy { (requireActivity()) }

    private lateinit var loadingFrg : LoadingFragment


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewClickEvents()
        registerObservers()
    }

    open fun registerObservers(){

        viewModel.errorLiveData.observe(viewLifecycleOwner){
            if (it.size != 0) showToast(requireContext(),it.first())
        }

    }

    open fun viewClickEvents(){}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.clearErrorLiveData()
    }



    open fun onScrollToTop() {}

    open fun onRetrievedTag(retrievedTag: String) {}

    open fun showLoading(container : Int){
        loadingFrg = LoadingFragment()
        mainHelper.supportFragmentManager.beginTransaction().addToBackStack("loadingFrg")
            .replace(container, loadingFrg, "loadingFrg").commit()
    }

    open fun hideLoading(){
        if (::loadingFrg.isInitialized) mainHelper.supportFragmentManager.beginTransaction().remove(loadingFrg).commit()
    }
}