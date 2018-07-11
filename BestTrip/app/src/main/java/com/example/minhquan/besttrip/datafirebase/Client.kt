package com.example.minhquan.besttrip.datafirebase

import java.io.Serializable

data class Location(var X: String , var Y: String )
data class Trip(var start: Location, var long: String, var end: Location, var usertaxi: String)
data class History(var listTrip: List<Trip>)
data class User(var email: String, var history: History?, var id: String?, var name: String, var password: String, var phone: String)
data class Client(var listUser: List<User>)