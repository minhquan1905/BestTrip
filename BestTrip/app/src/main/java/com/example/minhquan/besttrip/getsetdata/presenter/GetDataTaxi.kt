package com.example.minhquan.besttrip.getsetdata.presenter

import android.util.Log
import com.example.minhquan.besttrip.model.datafirebase.*
import com.example.minhquan.besttrip.getsetdata.view.Home
import com.example.minhquan.besttrip.login.view.ListTaxi
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class GetDataTaxi(val view :ListTaxi) {
    fun getDataTaxi(data : DatabaseReference) {
        data.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(dtSnap: DataSnapshot) {
                val ob = getTaxiX(dtSnap)
                Log.e("ob",ob.seater4.toString())
                view.showDataTaxi(ob)
            }

            override fun onCancelled(dtSnap: DatabaseError) {
                Log.d("getDataTaxi",dtSnap.toString())
            }
        })
    }

    fun getUserTaxiX(data: DataSnapshot) : UserTaxi{
        return UserTaxi(data.child("name").value.toString(),data.child("id").value.toString(),
                data.child("number_car").value.toString(),data.child("phone").value.toString(),
                data.child("price").value.toString(),data.child("image").value.toString())
    }
    fun getCompanyX(data: DataSnapshot) : Company{
        return Company(data.key.toString(), data.children.map { it -> getUserTaxiX(it) })
    }
    fun getSeaterX(data: DataSnapshot) : Seater{
        return Seater(data.children.map { it -> getCompanyX(it) })
    }
    fun getTaxiX(data: DataSnapshot) : Taxi{
        return Taxi(getSeaterX(data.child("Seater4")),getSeaterX(data.child("Seater7")), getSeaterX(data.child("Vip")))
    }

}