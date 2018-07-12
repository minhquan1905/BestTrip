package com.example.minhquan.besttrip.model.datafirebase

data class UserTaxi(val name: String, val id: String, val numberCar: String, val phone: String, val price: String)
data class Company(val listUser: List<UserTaxi>)
data class Seater4(val grab: Company, val mainlinh: Company, val phuongtrang: Company, val saigon: Company,
                   val vina: Company, val futune: Company, val savico: Company)
data class Seater7(val grab: Company, val mainlinh: Company, val phuongtrang: Company, val star: Company)
data class Vip(val saigon: Company, val savico: Company, val star: Company)
data class Taxi(val listseater4: List<Seater4>, val listseater7: List<Seater7>, val vip: List<Vip>)