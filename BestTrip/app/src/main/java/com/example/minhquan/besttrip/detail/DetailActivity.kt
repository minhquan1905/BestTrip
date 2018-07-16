package com.example.minhquan.besttrip.detail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.minhquan.besttrip.R
import com.example.minhquan.besttrip.getsetdata.presenter.SetDataHistory
import com.example.minhquan.besttrip.getsetdata.view.GetDataViewTaxiItf
import com.example.minhquan.besttrip.model.DetailRoute
import com.example.minhquan.besttrip.model.firebasedata.Taxi
import com.example.minhquan.besttrip.model.firebasedata.User
import kotlinx.android.synthetic.main.activity_detail.*
import android.content.Intent
import android.net.Uri


class DetailActivity : AppCompatActivity(){

    private lateinit var route: DetailRoute
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val bundle = intent.getBundleExtra("routeBundle")
        route  = bundle.getParcelable("DetailRoute") as DetailRoute
        user = intent.getSerializableExtra("DataUser") as User
        Log.d("Return route", route.price)

        setupView()
    }

    private fun setupView() {

        Glide.with(this)
                .load(route.company.listUser[0].image)
                .apply(RequestOptions.circleCropTransform())
                .into(imgCompany)

        label_distance.text = route.routes.legs!![0].distance!!.text
        label_money.text = route.price
        label_number.text = route.company.listUser[0].numberCar
        label_origin.text = route.routes.legs!![0].startAddress
        label_destination.text = route.routes.legs!![0].endAddress

        btnCheckout.setOnClickListener {
            SetDataHistory(user.name!!).createNewHistory(
                    route.routes.legs!![0].startAddress!!,
                    route.routes.legs!![0].distance!!.text!!,
                    route.routes.legs!![0].endAddress!!,
                    route.company.listUser[0].name!!)

            Toast.makeText(this, "Thanks for using our service!", Toast.LENGTH_SHORT).show()

            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:${route.company.listUser[0].phone}")
            startActivity(callIntent)
        }
    }

}
