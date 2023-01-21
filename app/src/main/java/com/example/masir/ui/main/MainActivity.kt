package com.example.masir.ui.main

import android.os.Bundle
import com.example.masir.R
import com.example.masir.databinding.ActivityMainBinding
import com.example.masir.utility.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutId: Int get() = R.layout.activity_main
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}