package com.example.minhquan.besttrip.getdata

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.minhquan.besttrip.R
import com.google.firebase.database.FirebaseDatabase


class GetDataView : AppCompatActivity(),GetDataViewItf {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        //Getdata from FireBase
        val database = FirebaseDatabase.getInstance().reference
        GetDataPresenter(this).getDataChild(database.child("Client").child("user1"))
    }

    override fun showDataChild(datachild: String){
        //txtdemo.text = datachild + "\n" + txtdemo.text
        Log.d("Show DataChild",datachild)
    }
}