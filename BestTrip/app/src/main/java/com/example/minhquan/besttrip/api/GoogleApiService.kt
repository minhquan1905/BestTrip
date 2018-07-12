package com.example.minhquan.besttrip.api

import com.example.minhquan.besttrip.model.ResultAddress
import com.example.minhquan.besttrip.model.ResultRoute
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface GoogleApiService {

    @GET("/maps/api/directions/json")
    fun getRoute(@Query("origin") origin: String,
                 @Query("destination") destination: String)
            : Observable<ResultRoute>

    @GET("maps/api/geocode/json")
    fun getAddress(@Query("latlng") latlng: String)
            : Observable<ResultAddress>


    @GET("/maps/api/{type}/json")
    fun getRoute_(@Path("type") type: String,
                  @Query("origin") origin: String,
                  @Query("destination") destination: String)
            : Observable<ResultRoute>
    fun getAddress_(@Path("type") type: String,
                    @Query("latlng") latlng: String)
            : Observable<ResultAddress>


    companion object {
        const val BASE_URL: String = "https://maps.googleapis.com"
    }
}