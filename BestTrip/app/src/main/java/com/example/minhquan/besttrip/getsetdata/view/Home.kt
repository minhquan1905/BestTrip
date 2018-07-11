package com.example.minhquan.besttrip.getsetdata.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.minhquan.besttrip.R
import com.example.minhquan.besttrip.datafirebase.Client
import com.example.minhquan.besttrip.datafirebase.User
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


    }

    override fun onResume() {
        super.onResume()
        //Getdata from FireBase
        val database = FirebaseDatabase.getInstance().reference
        GetDataPresenter(this).getDataChild(database.child("Client"))
    }



    override fun showDataChild(ob: Client){
        tvEmailFireBase.text = ob.arrayUser[0].email
        tvNameFireBase.text = ob.arrayUser[0].name
        // query email firebase
        //filter(ob)
        var user = GetDataPresenter(this).filterEmail(ob, this.emailUser)
        Log.d("Show DataChild",user[0].toString())
    }



}