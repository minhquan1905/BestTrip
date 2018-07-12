package com.example.minhquan.besttrip.getsetdata.presenter

import com.example.minhquan.besttrip.model.datafirebase.User
import com.google.firebase.database.FirebaseDatabase

class SetDataLoginGoogle() {
    fun createNewUserFireBase(email: String,name : String){
        val database = FirebaseDatabase.getInstance().reference

        val user = User(email,null,null,name, null,null,null)
        database.child("Client").child(name).setValue(user)
    }
}