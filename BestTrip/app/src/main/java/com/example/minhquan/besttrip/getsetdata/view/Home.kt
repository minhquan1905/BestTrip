package com.example.minhquan.besttrip.getsetdata.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.minhquan.besttrip.R
import com.example.minhquan.besttrip.datafirebase.Client
import com.example.minhquan.besttrip.getsetdata.presenter.GetDataPresenter
import com.google.firebase.database.FirebaseDatabase

class Home : AppCompatActivity(),GetDataViewItf {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
       // var emailUser = savedInstanceState?.get("emailUser")


    }

    override fun onResume() {
        super.onResume()
        //Getdata from FireBase
        val database = FirebaseDatabase.getInstance().reference
        GetDataPresenter(this).getDataChild(database.child("Client"))
    }



    override fun showDataChild(ob: Client){
//        var abc =
        //tvDetail.text = getDataX(abc)
        Log.d("xxxShow DataChild",ob.arrayUser[0].email)
    }

}