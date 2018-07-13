package com.example.minhquan.besttrip.detail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.minhquan.besttrip.R
import com.example.minhquan.besttrip.model.ResultRoute

class DetailActivity : AppCompatActivity() {

    lateinit var route: ResultRoute

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val bundle = intent.getBundleExtra("routeBundle")
        route  = bundle.getParcelable("selected_route") as ResultRoute

        Log.d("Return route", route.status)

    }
}
