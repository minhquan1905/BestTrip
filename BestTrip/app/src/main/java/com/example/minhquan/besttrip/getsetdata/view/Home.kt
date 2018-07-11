package com.example.minhquan.besttrip.getsetdata.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.minhquan.besttrip.R
import com.example.minhquan.besttrip.datafirebase.Client
import com.example.minhquan.besttrip.getsetdata.presenter.GetDataPresenter
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_home.*


class Home : AppCompatActivity(),GetDataViewItf {
    var emailUser : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var intent = intent
        emailUser = intent?.getStringExtra("emailUser")

        btnGetDataTaxi.setOnClickListener {
            //Getdata Taxi from FireBase
            val database = FirebaseDatabase.getInstance().reference
            GetDataPresenter(this).getDataTaxi(database.child("Taxi/Seater4/Grab/User1"))
        }
    }

    override fun onResume() {
        super.onResume()
        //Getdata Client from FireBase
        val database = FirebaseDatabase.getInstance().reference
        GetDataPresenter(this).getDataClient(database.child("Client"))
    }

    override fun showDataChild(ob: Client){
        // query email firebase
        var user = GetDataPresenter(this).filterEmail(ob, this.emailUser)
        Log.d("Show DataChild",user[0]?.toString())
        tvEmailFireBase.text = user[0].email
        tvNameFireBase.text = user[0].name
    }



}