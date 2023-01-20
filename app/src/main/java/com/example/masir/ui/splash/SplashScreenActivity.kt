package com.example.masir.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.masir.R
import com.example.masir.databinding.ActivitySplashScreenBinding
import com.example.masir.ui.main.MainActivity
import com.example.masir.utility.BaseActivity

class SplashScreenActivity : BaseActivity<ActivitySplashScreenBinding>() {
    override val layoutId: Int get() = R.layout.activity_splash_screen

    override fun initWhenUiCreated() {

        // go to main activity by 1s delay
        Handler(Looper.myLooper()!!).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1000)

    }
}