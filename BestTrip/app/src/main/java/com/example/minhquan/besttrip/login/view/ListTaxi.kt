package com.example.minhquan.besttrip.login.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button

import com.example.minhquan.besttrip.R
import com.example.minhquan.besttrip.adapter.TaxiAdapter
import com.example.minhquan.besttrip.getsetdata.presenter.GetDataTaxi
import com.example.minhquan.besttrip.getsetdata.view.GetDataViewTaxiItf
import com.example.minhquan.besttrip.model.mapdata.ResultRoute
import com.example.minhquan.besttrip.model.firebasedata.Company
import com.example.minhquan.besttrip.model.firebasedata.Taxi
import com.example.minhquan.besttrip.model.firebasedata.User
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_route.*
import kotlinx.android.synthetic.main.list_taxi.*

class ListTaxi : AppCompatActivity(), GetDataViewTaxiItf {

    private lateinit var rv4Seater: RecyclerView
    private lateinit var rv7Seater: RecyclerView
    private lateinit var rvPremium: RecyclerView
    private lateinit var taxiAdapter4: TaxiAdapter
    private lateinit var taxiAdapter7: TaxiAdapter
    private lateinit var taxiAdapterVip: TaxiAdapter


    private lateinit var route: ResultRoute
    private lateinit var user: User

    internal var button: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_taxi)

        rv4Seater = findViewById(R.id.rec4Seater)
        rv7Seater = findViewById(R.id.rec7Seater)
        rvPremium = findViewById(R.id.recPremium)


        setSupportActionBar(toolBar)
        supportActionBar?.title = "Choose your taxi"

        //Log.d("Return route", route.status)

        //Getdata Taxi from FireBase
        val database = FirebaseDatabase.getInstance().reference
        GetDataTaxi(this).getDataTaxi(database.child("Taxi"))

        setUpListView()

        imgButtonDown.setOnClickListener(View.OnClickListener {
//            val intent = Intent(this, RouteActivity::class.java)
//            startActivity(intent)
            finish()
            overridePendingTransition( R.anim.slide_in_down, R.anim.slide_out_down );
        })

    }

    private fun setUpListView() {

        val bundle = intent.getBundleExtra("routeBundle")
        route  = bundle.getParcelable("selected_route") as ResultRoute
        user = intent.getSerializableExtra("DataUser") as User

        taxiAdapter4 = TaxiAdapter(this@ListTaxi, route, user)
        taxiAdapter7 = TaxiAdapter(this@ListTaxi, route, user)
        taxiAdapterVip = TaxiAdapter(this@ListTaxi, route, user)
        rv4Seater.setHasFixedSize(true)
        rv7Seater.setHasFixedSize(true)
        rvPremium.setHasFixedSize(true)

        val layoutManager1 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv4Seater.layoutManager = layoutManager1
        val layoutManager2 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv7Seater.layoutManager = layoutManager2
        val layoutManager3 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvPremium.layoutManager = layoutManager3

        rv4Seater.adapter = taxiAdapter4
        rv7Seater.adapter = taxiAdapter7
        rvPremium.adapter = taxiAdapterVip

    }

    override fun showDataTaxi(ob: Taxi) {
        val listTaxi4 : List<Company> = ob.seater4.listCompany
        val listTaxi7 : List<Company> = ob.seater7.listCompany
        val listTaxiVip : List<Company> = ob.vip.listCompany

        taxiAdapter4.setData(listTaxi4)
        taxiAdapter7.setData(listTaxi7)
        taxiAdapterVip.setData(listTaxiVip)
    }
}
