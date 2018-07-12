package com.example.minhquan.besttrip.route

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
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
import android.os.Build
import android.os.Looper
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.example.minhquan.besttrip.login.view.MainActivity
import com.example.minhquan.besttrip.login.view.SplashScreen
import com.example.minhquan.besttrip.model.ResultAddress
import com.google.android.gms.location.*

import kotlinx.android.synthetic.main.nav_header.view.*


class RouteActivity :
        AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback,
        RouteContract.View {


    private val MY_PERMISSIONS_REQUEST_LOCATION = 99
    private val INITIAL_STROKE_WIDTH_PX = 5
    private val LEFT = 0
    private val RIGHT = 25
    private val TOP = 25
    private val BOTTOM = 325
    private val RADIUS_SMALL = 5.0
    private val RADIUS_LARGE = 100.0
    private val STROKE_WIDTH = 7f

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest : LocationRequest
    private lateinit var presenter: RouteContract.Presenter
    private lateinit var resultRoute: ResultRoute

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route)

        val mapFragment : SupportMapFragment? =
                supportFragmentManager.findFragmentById(R.id.mapView) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setupView()

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_logout -> {
                Toast.makeText(this,"Log out!!",Toast.LENGTH_LONG).show()
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        return true
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap?) {

        map = googleMap ?: return

        map.setPadding(LEFT, TOP, RIGHT, BOTTOM)

        locationRequest = LocationRequest.create()
        locationRequest.interval = 120000
        locationRequest.fastestInterval = 120000
        locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                fusedLocationClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper())
                map.isMyLocationEnabled = true
            } else {
                //Request Location Permission
                checkPermission()
            }
        }
        else {
            fusedLocationClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper())
            map.isMyLocationEnabled = true
        }

        RoutePresenter(this)

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

    private var mLocationCallback :LocationCallback = object:LocationCallback() {

        override fun onLocationResult(locationResult: LocationResult) {

            val locationList = locationResult.locations

            if (locationList.count() > 0) {
                //The last location in the list is the newest
                val location : Location = locationList[locationList.count() - 1]
                Log.i("MapsActivity", "Location: " + location.latitude + " " + location.longitude)

                //Move camera to last current location

                map.clear()

                val latLng = LatLng(location.latitude, location.longitude)
                val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15f)
                map.animateCamera(cameraUpdate)

                presenter.startGetAddress(location.latitude.toString() + "," + location.longitude.toString())

                map.addCircle(
                        CircleOptions().apply {
                            center(latLng )
                            radius(RADIUS_LARGE)
                            strokeWidth(STROKE_WIDTH)
                            strokeColor(R.color.colorPrimary)
                            fillColor(R.color.colorPrimary)
                        })


            }
        }
    }


    /**
     * Function for handle response data -Address- when request's successful
     * @param result : Response data - Address
     */
    override fun onGetAddressSuccess(result: ResultAddress) {
        Log.d("Data Status","Return Address success!")

        if (result.status == "OK")
            edit_origin.setText(result.results!![0].formattedAddress)
    }


    /**
     * Function for handle response data -Route- when request's successful
     * @param result : Response data - Route
     */
    override fun onGetRouteSuccess(result: ResultRoute) {

        Log.d("Data Status","Return route success!")

        if (result.status.toString() == "OK") {

            resultRoute = result

            edit_origin.setText(resultRoute.routes!![0].legs!![0].startAddress)
            edit_destination.setText(resultRoute.routes!![0].legs!![0].endAddress)
            drawRoute(map, decodePoly(resultRoute.routes!![0].overviewPolyline!!.points!!))

        }
        else
            showError("Something wrong with request!")
    }

    /**
     * Function checking permission for ACCESS_FINE_LOCATION
     */
    private fun checkPermission() {

        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        MY_PERMISSIONS_REQUEST_LOCATION)

                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(this,
                                    Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        //Location Permission already granted
                        fusedLocationClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper())
                        map.isMyLocationEnabled = true
                    }
                }

            }
        } else {
            // Permission has already been granted
            Log.d("Permission access","Permission has already been granted")

        }

    }

    /**
     * Function for setup listener for action buttons, actionbar
     */
    private fun setupView() {

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
        ){}

        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        // setup layout for navigationView header
        setUpViewHeader()

    }

    /**
     * Function for setup header
     */
    private fun setUpViewHeader() {
        val headerView = navigationView.inflateHeaderView(R.layout.nav_header)

        headerView.imgProfile.setImageResource(R.drawable.ic_car)
        headerView.tvUsername.text = "Ginn"

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
                color(R.color.colorPrimary)
                geodesic(true)
            }

        }

        val routePadding = 0
        val latLngBounds = boundsBuilder.build()

        map.setPadding(LEFT, TOP, RIGHT, BOTTOM)

        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, routePadding))
        googleMap.addPolyline(lineBuilder)

        map.addCircle(
                CircleOptions().apply {
                    center(lstLatLngRoute.first())
                    radius(RADIUS_SMALL)
                    strokeWidth(STROKE_WIDTH)
                    strokeColor(R.color.colorPrimary)
                    fillColor(R.color.colorPrimary)
                })

        googleMap.addMarker(MarkerOptions().apply{
            position(lstLatLngRoute.last())
            title("Destination")
            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        }).run {
            // add a tag to the marker
            tag = "Destination"
        }

    }

    /**
     * Method to decode polyline points
     * Courtesy : https://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
     */
    private fun decodePoly(encoded: String): List<LatLng> {
        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0

        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat

            shift = 0
            result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng

            val p = LatLng(lat.toDouble() / 1E5,
                    lng.toDouble() / 1E5)
            poly.add(p)
        }

        return poly
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



}

