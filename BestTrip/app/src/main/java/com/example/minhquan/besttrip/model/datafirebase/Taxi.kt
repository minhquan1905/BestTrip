package com.example.minhquan.besttrip.model.datafirebase

data class UserTaxi(val name: String, val id: String, val numberCar: String, val phone: String,
                    val price: String, val image : String)
data class Company(val name : String, val listUser: List<UserTaxi>)
data class Seater(val listCompany : List<Company>)
data class Taxi(val seater4 : Seater, val seater7 : Seater, val vip : Seater)