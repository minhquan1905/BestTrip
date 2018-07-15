package com.example.minhquan.besttrip.detail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.minhquan.besttrip.R
import com.example.minhquan.besttrip.model.DetailRoute

class DetailActivity : AppCompatActivity() {

    lateinit var route: DetailRoute

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val bundle = intent.getBundleExtra("routeBundle")
        route  = bundle.getParcelable("DetailRoute") as DetailRoute

        Log.d("Return route", route.price)

    }

    private fun setupView() {

    }


}
