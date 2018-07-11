package com.example.minhquan.besttrip.datafirebase

data class Location(var X: String , var Y: String )
data class Trip(var start: Location, var long: String, var end: Location, var usertaxi: String)
data class History(var arrayTrip: List<Trip>)
data class User(var email: String, var history: History?, var id: String?, var name: String, var password: String, var phone: String)
data class Client(var arrayUser: List<User>)