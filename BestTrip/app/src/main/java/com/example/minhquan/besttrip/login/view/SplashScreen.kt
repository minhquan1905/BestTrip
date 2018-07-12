package com.example.minhquan.besttrip.login.view

import android.content.Intent
import android.graphics.PixelFormat
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splashscreen.*

import com.example.minhquan.besttrip.R
import com.example.minhquan.besttrip.route.RouteActivity
import com.google.firebase.auth.FirebaseAuth

class SplashScreen : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private lateinit var splashTread: Thread

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val window = window
        window.setFormat(PixelFormat.RGBA_8888)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        startAnimations()

    }

    private fun startAnimations() {
        var loadAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_anim)
        loadAnimation.reset()

        linearLayout.clearAnimation()
        linearLayout.startAnimation(loadAnimation)

        loadAnimation = AnimationUtils.loadAnimation(this, R.anim.translate_anim)
        loadAnimation.reset()

        splash.clearAnimation()
        splash.startAnimation(loadAnimation)


        Handler().postDelayed({

            mAuth= FirebaseAuth.getInstance()
            var currentUser = mAuth!!.currentUser
            if (currentUser != null){
                val i = Intent(this@SplashScreen, RouteActivity::class.java)
                startActivity(i)
                this@SplashScreen.finish()
            }else{
                val i = Intent(this@SplashScreen, MainActivity::class.java)
                startActivity(i)
                this@SplashScreen.finish()
            }


        }, 3000)

    }

}
