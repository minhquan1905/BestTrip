package com.example.minhquan.besttrip.getsetdata.presenter

import android.util.Log
import com.example.minhquan.besttrip.datafirebase.*
import com.example.minhquan.besttrip.getsetdata.view.Home
import com.google.firebase.database.*

class GetDataPresenter(val view : Home) {
    //fun getvalue childen
    fun getDataClient(database : DatabaseReference){
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dtSnap: DataSnapshot) {

                val ob = getClientX(dtSnap)

                    Log.e("xxxxxxxxxxx2",ob.arrayUser[0].email)
                    view.showDataChild(ob)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("getDataClient",databaseError.toString())
            }
        })
    }

    fun getLocationX(data : DataSnapshot): Location {
        return Location(data.child("X").value.toString(),data.child("Y").value.toString())
    }
    fun getTripX(data: DataSnapshot): Trip {
        return Trip(getLocationX(data.child("start")),
                data.child("long").value.toString(),
                getLocationX(data.child("end")), data.child("usertaxi").value.toString())
    }
    fun getHistoryX(data: DataSnapshot) : History{
        return History(data.children.map { it -> getTripX(it) })
    }
    fun getUserX(data: DataSnapshot) : User{
        return User(data.child("email").value.toString(), getHistoryX(data.child("history")),
                data.child("id").value.toString(), data.child("name").value.toString(), data.child("password").value.toString(),
                data.child("phone").value.toString())
    }
    fun getClientX(data: DataSnapshot) : Client{
        return Client(data.children.map { it -> getUserX(it) })
    }

    fun filterEmail(ob : Client, emailUser : String?): List<User>{
        return ob.arrayUser.filter { it.email == emailUser }
    }

    //.................................................................................................................................
    fun getUserTaxiX(data: DataSnapshot) : UserTaxi{

        Log.d("getDataTaxi",data.toString())

        return UserTaxi(data.child("name").value.toString(),data.child("id").value.toString(),
                data.child("number_car").value.toString(),data.child("phone").value.toString(),
                data.child("price").value.toString())
    }

    fun getDataTaxi(database : DatabaseReference){
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dtSnap: DataSnapshot) {
                val ob = getClientX(dtSnap)

            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("getDataTaxi",databaseError.toString())
            }
        })
    }
    fun getGrabX(data: DataSnapshot) : Grab{
        return Grab(data.children.map { it -> getUserTaxiX(it) })
    }
//    fun getSeaterX(data: DataSnapshot) : Seater4{
//        return Seater4(getGrabX(data.child()))
//    }

}