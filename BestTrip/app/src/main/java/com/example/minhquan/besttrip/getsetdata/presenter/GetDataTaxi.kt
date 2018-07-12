package com.example.minhquan.besttrip.getsetdata.presenter

import android.util.Log
import com.example.minhquan.besttrip.model.datafirebase.*
import com.example.minhquan.besttrip.getsetdata.view.Home
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class GetDataTaxi(val view :Home) {
    fun getDataTaxi(data : DatabaseReference) {
        data.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(dtSnap: DataSnapshot) {
                val ob = getTaxiX(dtSnap)
                Log.e("ob",ob.toString())
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
                data.child("price").value.toString())
    }
    fun getCompanyX(data: DataSnapshot) : Company{
        return Company(data.children.map { it -> getUserTaxiX(it) })
    }
    fun getSeater4X(data: DataSnapshot) : Seater4{
        return Seater4(getCompanyX(data.child("Grab")),getCompanyX(data.child("MaiLinh")),getCompanyX(data.child("PhuongTrang")),
                getCompanyX(data.child("SaiGon")),getCompanyX(data.child("Vina")),getCompanyX(data.child("Future")),getCompanyX(data.child("Savico")))
    }
    fun getSeater7X(data: DataSnapshot): Seater7{
        return Seater7(getCompanyX(data.child("Grab")), getCompanyX(data.child("MaiLinh")),
                getCompanyX(data.child("PhuongTrang")), getCompanyX(data.child("Star")))
    }
    fun getVipX(data: DataSnapshot) : Vip{
        return Vip(getCompanyX(data.child("SaiGon")),getCompanyX(data.child("Savico")),getCompanyX(data.child("Star")))
    }
    fun getTaxiX(data: DataSnapshot) : Taxi{
        return Taxi(getSeater4X(data.child("Seater4")),getSeater7X(data.child("Seater7")), getVipX(data.child("Vip")))
    }

}