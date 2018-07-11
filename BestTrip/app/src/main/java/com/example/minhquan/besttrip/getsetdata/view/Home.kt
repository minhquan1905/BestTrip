package com.example.minhquan.besttrip.getsetdata.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.minhquan.besttrip.R
import com.example.minhquan.besttrip.datafirebase.Client
import com.example.minhquan.besttrip.datafirebase.User
import com.example.minhquan.besttrip.getsetdata.presenter.GetDataLogin
import com.example.minhquan.besttrip.getsetdata.presenter.GetDataTaxi
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_home.*


class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    override fun onResume() {
        super.onResume()
        //Getdata Taxi from FireBase
        val database = FirebaseDatabase.getInstance().reference
        GetDataTaxi(this).getDataTaxi(database.child("Taxi/Seater4/MaiLinh"))
    }
}