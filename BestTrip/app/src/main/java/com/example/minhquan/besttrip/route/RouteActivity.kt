package com.example.minhquan.besttrip.route

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.minhquan.besttrip.R
import com.example.minhquan.besttrip.model.ResultRoute
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_route.*
import android.location.LocationManager

import kotlinx.android.synthetic.main.nav_header.view.*


class RouteActivity :
        AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback,
        LocationListener,
        RouteContract.View {


    private val MIN_TIME: Long = 400
    private val MIN_DISTANCE = 1000f
    private val INITIAL_STROKE_WIDTH_PX = 5

    private lateinit var map: GoogleMap
    private lateinit var locationManager: LocationManager
    private lateinit var presenter: RouteContract.Presenter


    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route)

        val mapFragment : SupportMapFragment? =
                supportFragmentManager.findFragmentById(R.id.mapView) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this)

        setSupportActionBar(toolBar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navigationView.bringToFront()
        navigationView.setNavigationItemSelectedListener(this)

        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolBar,
                R.string.open,
                R.string.close
        ){

        }

        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        // setup layout for navigationView header
        setUpViewHeader()

    }

    private fun setUpViewHeader() {
        val headerView = navigationView.inflateHeaderView(R.layout.nav_header)

        headerView.imgProfile.setImageResource(R.drawable.ic_car)
        headerView.tvUsername.text = "Ginn"

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_logout -> {
                Toast.makeText(this,"abccccc",Toast.LENGTH_LONG).show()
                FirebaseAuth.getInstance().signOut()
            }
        }

        return true
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap ?: return
        with(googleMap) {}

        RoutePresenter(this)
        setupView()

    }

    /**
     * Function for handle response data when request's successful
     * @param result : Response data
     */
    override fun onGetRouteSuccess(result: ResultRoute) {

        Log.d("Data Status","Return success!")

        if (result.status.toString() == "OK") {

            val route = result.routes!![0].legs!![0].steps!!.map { it ->
               LatLng(it.startLocation!!.lat!!, it.startLocation.lng!!)
            }

            drawRoute(map, route)

        }
        else
            showError("Something wrong with request!")
    }

    /**
     * Function for setup listener for action buttons
     */
    private fun setupView() {

        btnCancel.setOnClickListener {
            edit_origin.setText("")
            edit_destination.setText("")
            map.clear()
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

    /**
     * Zooms a Route (given a List of LalLng) at the greatest possible zoom level, draw a direction
     * given location and set up marker
     *
     * @param googleMap: instance of GoogleMap
     * @param lstLatLngRoute: list of LatLng forming Route
     */
    private fun drawRoute(googleMap: GoogleMap, lstLatLngRoute: List<LatLng>) {

        val boundsBuilder = LatLngBounds.Builder()
        val lineBuilder = PolylineOptions()


        for (latLngPoint in lstLatLngRoute) {
            boundsBuilder.include(latLngPoint)

            // A geodesic polyline that goes form origin to destination.
            lineBuilder.apply {
                add(latLngPoint)
                width(INITIAL_STROKE_WIDTH_PX.toFloat())
                color(Color.BLUE)
                geodesic(true)
            }

        }

        val routePadding = 0
        val latLngBounds = boundsBuilder.build()

        googleMap.setPadding(100,100,100,200)

        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, routePadding))
        googleMap.addPolyline(lineBuilder)

        googleMap.addMarker(MarkerOptions().apply{
            position(lstLatLngRoute.first())
            title("Origin")
            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
        })

        googleMap.addMarker(MarkerOptions().apply{
            position(lstLatLngRoute.last())
            title("Destination")
            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        })

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

    override fun onLocationChanged(location: Location?) {
        val latLng = LatLng(location!!.latitude, location.longitude)
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10f)
        map.animateCamera(cameraUpdate)
        locationManager.removeUpdates(this)
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

    override fun onProviderEnabled(provider: String?) {}

    override fun onProviderDisabled(provider: String?) {}



}