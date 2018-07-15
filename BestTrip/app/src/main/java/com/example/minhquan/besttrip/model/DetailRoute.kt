package com.example.minhquan.besttrip.model


import android.os.Parcel
import com.example.minhquan.besttrip.model.datafirebase.Company
import com.example.minhquan.besttrip.utils.KParcelable
import com.example.minhquan.besttrip.utils.parcelableCreator


data class DetailRoute(val routes: Route, val company: Company, val price: String) : KParcelable {
    constructor(parcel: Parcel) : this(
            parcel.readParcelable(Route::class.java.classLoader),
            parcel.readParcelable(Company::class.java.classLoader),
            parcel.readString())

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeParcelable(routes, flags)
        writeParcelable(company, flags)
        writeString(price)
    }


    companion object {
        @JvmField val CREATOR = parcelableCreator(::DetailRoute)
    }
}