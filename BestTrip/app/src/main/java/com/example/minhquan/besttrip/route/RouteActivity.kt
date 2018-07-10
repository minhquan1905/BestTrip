package com.example.minhquan.besttrip.route

import android.annotation.SuppressLint
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.minhquan.besttrip.R
import com.example.minhquan.besttrip.model.ResultRoute
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_route.*

class RouteActivity :
        AppCompatActivity(),
        GoogleMap.OnCameraMoveStartedListener,
        GoogleMap.OnCameraMoveListener,
        GoogleMap.OnCameraMoveCanceledListener,
        GoogleMap.OnCameraIdleListener,
        OnMapReadyCallback,
        RouteContract.View {

    private val INITIAL_STROKE_WIDTH_PX = 5

    private lateinit var map: GoogleMap
    private lateinit var presenter: RouteContract.Presenter


    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route)
        val mapFragment : SupportMapFragment? =
                supportFragmentManager.findFragmentById(R.id.mapView) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        setSupportActionBar(toolBar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navigationView.setNavigationItemSelectedListener {
            when(it.itemId){

            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolBar,
                R.string.open,
                R.string.close
        ){
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
            }
        }

        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap ?: return
        with(googleMap) {
            setOnCameraIdleListener(this@RouteActivity)
            setOnCameraMoveStartedListener(this@RouteActivity)
            setOnCameraMoveListener(this@RouteActivity)
            setOnCameraMoveCanceledListener(this@RouteActivity)
        }

        RoutePresenter(this)
        setupView()

    }

    override fun onCameraMoveStarted(p0: Int) {

    }

    override fun onCameraMove() {

    }

    override fun onCameraMoveCanceled() {

    }

    override fun onCameraIdle() {

    }

    override fun showProgress(isShow: Boolean) {
        loader.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun setPresenter(presenter: RouteContract.Presenter) {
        this.presenter = presenter
    }

    /**
     * Function for handle response data when request's successful
     * @param result : Response data
     */
    override fun onGetRouteSuccess(result: ResultRoute) {

        Log.d("Data Status","Return success!")

        if (result.status.toString() == "OK") {

            val latFirst = result.routes!![0].bounds!!.northeast!!.lat
            val lngFirst = result.routes[0].bounds!!.northeast!!.lng
            val latSecond = result.routes[0].bounds!!.southwest!!.lat
            val lngSecond = result.routes[0].bounds!!.southwest!!.lng

            val routeBound = LatLngBounds(
                    LatLng(latFirst!!, lngFirst!!),
                    LatLng(latSecond!!, lngSecond!!))

            // Set the camera to the greatest possible zoom level that includes the bounds
            map.moveCamera(CameraUpdateFactory.newLatLngBounds(routeBound, 10))

            result.routes[0].legs!![0].steps!!.forEach { it ->
                map.addPolyline(PolylineOptions().apply {
                    add( LatLng(it.startLocation!!.lat!!, it.startLocation.lng!!) )
                    width(INITIAL_STROKE_WIDTH_PX.toFloat())
                    color(Color.BLUE)
                    geodesic(true)
                })
            }

        }
        else
            showError("Something wrong with request!")
    }

    /**
     * Function for setup popup listener button
     */
    private fun setupView() {

        btnCancel.setOnClickListener {
            edit_origin.setText("")
            edit_destination.setText("")
        }

        btnFind.setOnClickListener {
            val origin: String = edit_origin.text.toString()
            val destination: String = edit_destination.text.toString()

            if (origin == "")
                Toast.makeText(this, "Origin location can not be empty", Toast.LENGTH_SHORT).show()
            else if (destination == "")
                Toast.makeText(this, "Destination location can not be empty", Toast.LENGTH_SHORT).show()
            else if (origin != "" && destination != "")
                presenter.startGetRoute(origin, destination)
        }
    }


}