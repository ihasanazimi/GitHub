package com.example.masir.utility

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.example.masir.utility.extentions.showToast

abstract class BaseDialogFragmentByVM<V : ViewDataBinding , VM : BaseViewModel> :
    DialogFragment() {

    private var _binding: V? = null
    abstract val viewModel: VM
    val binding get() = _binding!!
    val mainHelper by lazy { (requireActivity()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickEvents()
        registerObservers()
    }

    open fun registerObservers(){

        viewModel.errorLiveData.observe(viewLifecycleOwner){
            if (it.size != 0) showToast(requireContext(),it.first())
        }

    }

    open fun clickEvents(){}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.clearErrorLiveData()
    }

    @get:LayoutRes
    abstract val layoutId: Int

    open fun onScrollToTop() {}


    override fun onStart() {
        super.onStart()
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)

        val width = displayMetrics.widthPixels
        val height = displayMetrics.heightPixels

        dialog?.window?.setLayout(width-82, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}