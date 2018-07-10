package com.example.minhquan.besttrip.login.view

import android.content.Intent
import android.graphics.PixelFormat
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splashscreen.*

import com.example.minhquan.besttrip.R

class SplashScreen : AppCompatActivity() {

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
        //val linearLayout = findViewById<View>(R.id.lin_lay) as LinearLayout
        linearLayout.clearAnimation()
        linearLayout.startAnimation(loadAnimation)

        loadAnimation = AnimationUtils.loadAnimation(this, R.anim.translate_anim)
        loadAnimation.reset()
        //val imageView = findViewById<View>(R.id.splash) as ImageView
        splash.clearAnimation()
        splash.startAnimation(loadAnimation)

        splashTread = object : Thread() {
            override fun run() {
                try {
                    var waited = 0
                    while (waited < 3500) {
                        Thread.sleep(100)
                        waited += 100
                    }
                    val intent = Intent(this@SplashScreen,
                            MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                    startActivity(intent)
                    this@SplashScreen.finish()
                } catch (e: InterruptedException) {

                } finally {
                    this@SplashScreen.finish()
                }

            }
        }
        splashTread.start()

    }

}
