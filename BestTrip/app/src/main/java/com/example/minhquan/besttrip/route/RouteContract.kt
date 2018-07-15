package com.example.minhquan.besttrip.route

import com.example.minhquan.besttrip.model.ResultAddress
import com.example.minhquan.besttrip.model.ResultRoute


interface RouteContract {

    interface View {

        fun showProgress(isShow: Boolean)

        fun showError(message: String)

        fun setPresenter(presenter: RouteContract.Presenter)

        fun onGetRouteSuccess(result: ResultRoute)

        fun onGetAddressSuccess(result: ResultAddress)
    }

    interface Presenter {

        fun startGetRoute(org: String, des: String)

        fun startGetAddress(latlng: String)
    }
}