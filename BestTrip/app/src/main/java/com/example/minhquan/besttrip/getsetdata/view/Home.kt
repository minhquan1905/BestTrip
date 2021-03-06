package com.example.minhquan.besttrip.getsetdata.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.minhquan.besttrip.R
import com.example.minhquan.besttrip.getsetdata.presenter.SetDataHistory
import com.example.minhquan.besttrip.model.firebasedata.Taxi
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_home.*


class Home : AppCompatActivity(),GetDataViewTaxiItf {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        // Get Data User
        /*val i = intent
        val user : User = i.getSerializableExtra("DataUser") as User
        tvNameTaxi.text = user.email.toString()*/


        // Creater History
        btnNewHistory.setOnClickListener {
            SetDataHistory("user1").createNewHistory("KTX khu A","5km","KTX khu B", "Nguyen Van X")
        }

    }

    override fun onResume() {
        super.onResume()
        //Getdata Taxi from FireBase
        val database = FirebaseDatabase.getInstance().reference
        //GetDataTaxi(this).getDataTaxi(database.child("Taxi"))
    }

    override fun showDataTaxi(ob: Taxi) {
        tvNameTaxi.text = ob.seater4.listCompany[1].listUser[1].toString()
    }


}