package com.example.minhquan.besttrip.model.datafirebase

import android.os.Parcelable
import java.io.Serializable

//data class Location(var X: String , var Y: String ):Serializable
data class Trip(var start: String?, var long: String?, var end: String?, var usertaxi: String?):Serializable
data class History(var listTrip: List<Trip>) :Serializable
data class User(var email: String?, var history: History?, var id: String?, var name: String?, var password: String?, var phone: String?,var image: String?) : Serializable
data class Client(var listUser: List<User>):Serializable