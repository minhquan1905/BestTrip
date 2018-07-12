package com.example.minhquan.besttrip.model.datafirebase

import java.io.Serializable

data class Location(var X: String , var Y: String ):Serializable
data class Trip(var start: Location, var long: String, var end: Location, var usertaxi: String):Serializable
data class History(var listTrip: List<Trip>) :Serializable
data class User(var email: String?, var history: History?, var id: String?, var name: String?, var password: String?, var phone: String?) : Serializable
data class Client(var listUser: List<User>)