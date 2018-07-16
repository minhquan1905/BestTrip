package com.example.minhquan.besttrip.model.firebasedata

import android.os.Parcel
import com.example.minhquan.besttrip.utils.KParcelable
import com.example.minhquan.besttrip.utils.parcelableCreator

data class UserTaxi(val name: String?, val id: String?, val numberCar: String?, val phone: String?,
                    val price: String?, val image: String?) : KParcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeString(id)
        writeString(numberCar)
        writeString(phone)
        writeString(price)
        writeString(image)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::UserTaxi)
    }
}

data class Company(val name: String?, val listUser: List<UserTaxi>) : KParcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            ArrayList<UserTaxi>().apply { source.readList(this, UserTaxi::class.java.classLoader) }
    )

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeList(listUser)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Company)
    }
}

data class Seater(val listCompany: List<Company>) : KParcelable {
    constructor(source: Parcel) : this(
            source.createTypedArrayList(Company.CREATOR))

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeTypedList(listCompany)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Seater)
    }
}

data class Taxi(val seater4 : Seater, val seater7 : Seater, val vip : Seater) : KParcelable {
    constructor(parcel: Parcel) : this(
            parcel.readParcelable(Seater::class.java.classLoader),
            parcel.readParcelable(Seater::class.java.classLoader),
            parcel.readParcelable(Seater::class.java.classLoader))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(seater4, flags)
        parcel.writeParcelable(seater7, flags)
        parcel.writeParcelable(vip, flags)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Taxi)
    }
}