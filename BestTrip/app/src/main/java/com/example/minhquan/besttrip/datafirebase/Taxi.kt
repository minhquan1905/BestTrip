package com.example.minhquan.besttrip.datafirebase

data class UserTaxi(val name: String, val id: String, val numberCar: String, val phone: String, val price: String)
data class Company(val listUser: List<UserTaxi>)
data class Seater(val grab: Company, val mainlinh: Company, val phuongtrang: Company)
data class Taxi(val listtaxi: List<Seater>)