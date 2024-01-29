package com.gwook.timeearning.api

import android.app.Application
import android.content.Context
import com.gwook.timeearning.api.HttpUtils.getRetrofit
import com.gwook.timeearning.api.HttpUtils.okHttp
import com.gwook.timeearning.base.BaseView
import com.gwook.timeearning.bean.Base
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.schedulers.Schedulers

class CommonApi {
    private val service: CommonApiService

    constructor() {
        service = getRetrofit(okHttp, "").create(
            CommonApiService::class.java
        )
    }

    constructor(url: String?) {
        service = getRetrofit(okHttp, url).create(
            CommonApiService::class.java
        )
    }

    private fun sub(
        observable: Observable<*>,
        baseView: BaseView<*>,
        requestCode: Int
    ): CommonObserver<*>? {
        return try {
            val observer: CommonObserver<*> = CommonObserver<Base>(baseView, requestCode)
            observable.subscribeOn(Schedulers.newThread()) //子线程访问网络
                .observeOn(AndroidSchedulers.mainThread()).subscribe(observer as Observer<in Any>) //回调到主线程
            observer
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun sub(
        observable: Observable<*>,
        baseView: BaseView<*>,
        requestCode: Int,
        isShowLoading: Boolean
    ): CommonObserver<*> {
        val observer: CommonObserver<*> = CommonObserver<Base>(baseView, requestCode, isShowLoading)
        observable.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(observer as Observer<in Any>)
        return observer
    }

    /********************************************** 登录  */ //    public void verificationCode(String mobileNo, BaseView baseView, int requestCode) {
    //        sub(service.verificationCode(mobileNo), baseView, requestCode);
    //    }
    //
    //    public CommonObserver queryUserList(String userPhone, String password, String type, String cid, BaseView baseView, int requestCode, Boolean isShowLoading) {
    //        return sub(service.queryUserlist(userPhone, password, type, cid), baseView, requestCode, isShowLoading);
    //    }
    //
    //    public void forgetPassword(String mobileNo, BaseView baseView, int requestCode) {
    //        sub(service.forgetPassword(mobileNo), baseView, requestCode);
    //    }
    fun updatePassWord(
        oldPassword: String?,
        newPassword: String?,
        userPhone: String?,
        baseView: BaseView<*>,
        requestCode: Int
    ) {
        sub(
            service.updatePassWord(oldPassword!!, newPassword!!, userPhone!!),
            baseView,
            requestCode
        )
    }

    companion object {
        private var instance: CommonApi? = null
        fun getInstance(context: Context?): CommonApi? {
            if (context !is Application) {
                throw RuntimeException("context must be Application")
            }
            if (instance == null) {
                instance = CommonApi()
            }
            return instance
        }

        fun getInstance(): CommonApi? {
            if (instance == null) {
                instance = CommonApi()
            }
            return instance
        }
    }
}