package com.example.minhquan.besttrip.getdata

import android.util.Log
import com.google.firebase.database.*

class GetDataPresenter(val view : GetDataView) {
    //fun getvalue childen
    fun getDataChild(database : DatabaseReference){
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                    view.showDataChild(postSnapshot.value.toString())
                    Log.d("getDataChild",postSnapshot.value.toString())
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("getDataChild",databaseError.toString())
            }
        })
    }
}