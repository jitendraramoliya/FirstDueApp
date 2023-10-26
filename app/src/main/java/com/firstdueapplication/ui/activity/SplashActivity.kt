package com.firstdueapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.firstdueapplication.R
import com.firstdueapplication.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashActivity:AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        InitializeAnimIcon()
        InitializeSplash()

    }

    private fun InitializeAnimIcon() {
        val clk_rotate = AnimationUtils.loadAnimation(
            this,
            R.anim.rotate_clockwise
        )
        binding.ivSplash.startAnimation(clk_rotate)
    }

    private fun InitializeSplash() {
        Handler().postDelayed(Runnable {
            //This method will be executed once the timer is over
            // Start your app main activity
            val i = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(i)
            // close this activity
            finish()
        }, 3000)
    }

}