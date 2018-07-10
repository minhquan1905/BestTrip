package com.example.minhquan.besttrip.route

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
import kotlinx.android.synthetic.main.activity_route.*

class RouteActivity :
        AppCompatActivity(),
        GoogleMap.OnCameraMoveStartedListener,
        GoogleMap.OnCameraMoveListener,
        GoogleMap.OnCameraMoveCanceledListener,
        GoogleMap.OnCameraIdleListener,
        OnMapReadyCallback,
        RouteContract.View {


    private lateinit var map: GoogleMap
    private lateinit var presenter: RouteContract.Presenter


    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route)
        val mapFragment : SupportMapFragment? =
                supportFragmentManager.findFragmentById(R.id.mapView) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
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
    override fun onGetRouteSuccess(result: ResultRoute?) {

        Log.d("Data Status","Return success!")

        if (result?.status.toString() == "OK") {

            // Set the camera to the greatest possible zoom level that includes the bounds
            val routeBound = LatLngBounds(
                    LatLng(-44.0, 113.0),
                    LatLng(-10.0, 154.0))
            map.moveCamera(CameraUpdateFactory.newLatLngBounds(routeBound, 0))

            val latOrigin = result?.routes?.get(0)?.legs?.get(0)?.endLocation?.lat.toString()
            val lngOrigin = result?.routes?.get(0)?.legs?.get(0)?.endLocation?.lng.toString()
            val latDestination = result?.routes?.get(0)?.legs?.get(0)?.startLocation?.lat.toString()
            val lngDestination = result?.routes?.get(0)?.legs?.get(0)?.startLocation?.lng.toString()

            Log.d("Origin location return", "$latOrigin-$lngOrigin")
            Log.d("Des location return", "$latDestination-$lngDestination")


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