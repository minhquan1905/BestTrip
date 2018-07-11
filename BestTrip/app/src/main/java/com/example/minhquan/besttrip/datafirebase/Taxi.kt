package com.example.minhquan.besttrip.datafirebase

data class UserTaxi(val name: String, val id: String, val numberCar: String, val phone: String, val price: String)
data class Grab(val listUser: List<UserTaxi>)
data class MaiLinh(val listUser: List<UserTaxi>)
data class PhuongTrang(val listUser: List<UserTaxi>)
data class Seater4(val grab: Grab, val mainlinh: MaiLinh, val phuongTrang: PhuongTrang)
data class Seater7(val grab: Grab, val mainlinh: MaiLinh, val phuongTrang: PhuongTrang)
data class Vip(val grab: Grab, val mainlinh: MaiLinh, val phuongTrang: PhuongTrang)