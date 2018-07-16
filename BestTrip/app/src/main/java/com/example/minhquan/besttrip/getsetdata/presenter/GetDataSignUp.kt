package com.example.minhquan.besttrip.getsetdata.presenter

import android.util.Log
import com.example.minhquan.besttrip.model.firebasedata.*
import com.example.minhquan.besttrip.login.view.SignUp
import com.google.firebase.database.*

class GetDataSignUp(val view : SignUp) {
    //fun getvalue Client
    fun getDataClient(database : DatabaseReference){
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dtSnap: DataSnapshot) {
                val ob = getClientX(dtSnap)

                Log.e("test user[0]",ob.listUser[0].email)

                if (view.tag != null)
                    view.showDataClient(ob)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("getDataClient",databaseError.toString())
            }
        })
    }

    fun getTripX(data: DataSnapshot): Trip {
        return Trip(data.child("start").value.toString(), data.child("long").value.toString(),
                data.child("end").value.toString(), data.child("usertaxi").value.toString())
    }
    fun getHistoryX(data: DataSnapshot) : History{
        return History(data.children.map { it -> getTripX(it) })
    }
    fun getUserX(data: DataSnapshot) : User{
        return User(data.child("email").value.toString(), getHistoryX(data.child("history")),
                data.child("id").value.toString(), data.child("name").value.toString(), data.child("password").value.toString(),
                data.child("phone").value.toString(),data.child("image").value.toString())
    }
    fun getClientX(data: DataSnapshot) : Client{
        return Client(data.children.map { it -> getUserX(it) })
    }

    fun filterEmail(ob : Client, emailUser : String?): List<User>{
        return ob.listUser.filter { it.email == emailUser }
    }

}