package com.example.minhquan.besttrip.getsetdata.presenter

import android.util.Log
import com.example.minhquan.besttrip.getsetdata.view.Home
import com.example.minhquan.besttrip.model.datafirebase.History
import com.example.minhquan.besttrip.model.datafirebase.Trip
import com.google.firebase.database.FirebaseDatabase

class SetDataHistory(val nameUser : String) {
    fun createNewHistory(start: String, long: String, end: String, usertaxi: String) {
        val database = FirebaseDatabase.getInstance().reference
        val trip = Trip(start, long, end, usertaxi)
        database.child("Client").child(nameUser).child("history").push().setValue(trip)
        Log.e("SetDataHistory","set OK")
    }
}