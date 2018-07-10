package com.example.minhquan.besttrip.route

import com.example.minhquan.besttrip.model.ResultRoute
import com.example.minhquan.besttrip.utils.RetrofitUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class RoutePresenter(private var view: RouteContract.View) : RouteContract.Presenter {


    //Instance of interface created for Retrofit API calls
    private val service by lazy {
        //Initializing Retrofit stuff
        RetrofitUtil.builderGoogleService()
    }

    //Rx Java object that tracks fetching activity
    private var disposable: Disposable? = null

    init {
        this.view.setPresenter(this)
    }

    /**
     * Service to get data of route from Google Map database
     * @param org : The origin place
     * @param des : The destination place
     */
    override fun startGetRoute(org: String, des: String) {
        view.showProgress(true)
        disposable  = service.getRoute(org, des)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result: ResultRoute? ->
                    view.showProgress(false)
                    view.onGetRouteSuccess(result!!)
                }, { error ->
                    view.showProgress(false)
                    view.showError(error.localizedMessage)
                })
    }


}