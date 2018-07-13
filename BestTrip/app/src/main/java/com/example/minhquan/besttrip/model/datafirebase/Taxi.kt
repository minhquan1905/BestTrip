package com.example.minhquan.besttrip.model.datafirebase

data class UserTaxi(val name: String, val id: String, val numberCar: String, val phone: String, val price: String)
data class Company(val name : String, val listUser: List<UserTaxi>)
data class Seater(val listCompany : List<Company>)
/*data class Seater(val grab: Company, val mainlinh: Company, val phuongtrang: Company, val saigon: Company,
                   val vina: Company, val futune: Company, val savico: Company)
data class Seater7(val grab: Company, val mainlinh: Company, val phuongtrang: Company, val star: Company)
data class Vip(val saigon: Company, val savico: Company, val star: Company)*/
data class Taxi(val seater4 : Seater, val seater7 : Seater, val vip : Seater)