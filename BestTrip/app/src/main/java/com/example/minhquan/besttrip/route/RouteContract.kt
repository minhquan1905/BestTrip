package com.example.minhquan.besttrip.route

import com.example.minhquan.besttrip.base.BaseContract
import com.example.minhquan.besttrip.model.ResultRoute


interface RouteContract {

    interface View: BaseContract.View {

        fun showProgress(isShow: Boolean)

        fun showError(message: String)

        fun setPresenter(presenter: RouteContract.Presenter)

        fun onGetRouteSuccess(result: ResultRoute?)
    }

    interface Presenter: BaseContract.Presenter<RouteContract.View> {

        fun startGetRoute(org: String, des: String)


    }
}