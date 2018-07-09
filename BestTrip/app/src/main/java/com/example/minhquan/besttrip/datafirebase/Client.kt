package com.example.minhquan.besttrip.datafirebase

data class Place(val X:String,val Y:String )
data class History(val trip: Trip)
data class Trip(val loacation: Place,val long: String,val place: Place,val usertaxi : String)
data class User(val name: String,val id: Int, val password: String,val place: Place,val history: History)
data class Client(val user: User)