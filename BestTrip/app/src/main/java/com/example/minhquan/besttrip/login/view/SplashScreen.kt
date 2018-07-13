package com.example.minhquan.besttrip.login.view

import android.content.Intent
import android.graphics.PixelFormat
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splashscreen.*

import com.example.minhquan.besttrip.R
import com.example.minhquan.besttrip.getsetdata.presenter.GetDataLogin
import com.example.minhquan.besttrip.getsetdata.presenter.GetDataSplashScrean
import com.example.minhquan.besttrip.getsetdata.view.GetDataViewClientItf
import com.example.minhquan.besttrip.getsetdata.view.GetDataViewTaxiItf
import com.example.minhquan.besttrip.getsetdata.view.Home
import com.example.minhquan.besttrip.model.datafirebase.Client
import com.example.minhquan.besttrip.model.datafirebase.Taxi
import com.example.minhquan.besttrip.route.RouteActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SplashScreen : AppCompatActivity(),GetDataViewClientItf {

    private var mAuth: FirebaseAuth? = null
    private lateinit var splashTread: Thread
    var emailUser : String = ""

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
                emailUser = currentUser.email.toString()
                //getDataClient from Firebase
                val database = FirebaseDatabase.getInstance().reference
                GetDataSplashScrean(this).getDataClient(database.child("Client"))
                //------------------------> fun showDataClient
            }else{
                val i = Intent(this@SplashScreen, MainActivity::class.java)
                startActivity(i)
                this@SplashScreen.finish()
            }


        }, 3000)

    }
    override fun showDataClient(ob: Client) {
        val user = GetDataSplashScrean(this).filterEmail(ob, this.emailUser)
        Log.d("DataUser",user[0].toString())

        val i = Intent(this@SplashScreen, Home::class.java)
        intent.putExtra("DataUser",user[0])
        startActivity(i)
        this@SplashScreen.finish()
    }

}
