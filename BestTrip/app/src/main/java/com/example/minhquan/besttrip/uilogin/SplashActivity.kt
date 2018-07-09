package com.example.minhquan.besttrip.uilogin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.example.minhquan.besttrip.R


class SplashActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_layout)

        /* Create an Intent that will start the Menu-Activity. */
        Handler().postDelayed(Runnable {
            val mainIntent = Intent(this@SplashActivity, MainActivity::class.java)
            this@SplashActivity.startActivity(mainIntent)
            this@SplashActivity.finish()
        }, 3000)
    }
}