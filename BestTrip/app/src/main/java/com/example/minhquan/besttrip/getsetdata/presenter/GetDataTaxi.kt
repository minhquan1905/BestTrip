package com.example.minhquan.besttrip.getsetdata.presenter

import android.util.Log
import com.example.minhquan.besttrip.model.datafirebase.*
import com.example.minhquan.besttrip.getsetdata.view.Home
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class GetDataTaxi(view : Home) {
    fun getDataTaxi(data : DatabaseReference){
        data.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(dtSnap: DataSnapshot) {
                val ob = getCompanyX(dtSnap)
                Log.e("ob",ob.toString())
            }

            override fun onCancelled(dtSnap: DatabaseError) {
                Log.d("getDataTaxi",dtSnap.toString())
            }
        })
    }

    fun getUserTaxiX(data: DataSnapshot) : UserTaxi{
        return UserTaxi(data.child("name").value.toString(),data.child("id").value.toString(),
                data.child("number_car").value.toString(),data.child("phone").value.toString(),
                data.child("price").value.toString())
    }
    fun getCompanyX(data: DataSnapshot) : Company{
        return Company(data.children.map { it -> getUserTaxiX(it) })
    }
    fun getSeaterX(data: DataSnapshot) : Seater{
        return Seater(getCompanyX(data.child("Grab")),getCompanyX(data.child("MaiLinh")),getCompanyX(data.child("PhuongTrang")))
    }
    fun getTaxiX(data: DataSnapshot) : Taxi{
        return Taxi(data.children.map { it -> getSeaterX(it) })
    }

}