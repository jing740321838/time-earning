package com.gwook.timeearning.api

import com.gwook.timeearning.bean.Base
import com.gwook.timeearning.base.BaseView
import io.reactivex.rxjava3.disposables.Disposable
import android.text.TextUtils
import io.reactivex.rxjava3.core.Observer
import retrofit2.HttpException
import java.lang.Exception
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.text.ParseException

/**
 * 对返回数据进行统一的处理
 *
 * @param <T>要求数据格式为Base
</T> */
class CommonObserver<T : Base?> : Observer<T> {
    private var isShowLoading = false
    private var baseView: BaseView<*>
    private var requestCode: Int
    private var curDisposable: Disposable? = null
    private var isCanceled = false

    internal constructor(baseView: BaseView<*>, requestCode: Int) {
        this.baseView = baseView
        this.requestCode = requestCode
    }

    internal constructor(baseView: BaseView<*>, requestCode: Int, isShowLoading: Boolean) {
        this.baseView = baseView
        this.requestCode = requestCode
        this.isShowLoading = isShowLoading
    }

    override fun onSubscribe(d: Disposable) {
        curDisposable = d
        if (isCanceled) {
            cancel()
        }
        if (isShowLoading) {
            baseView.loading()
        }
    }

    override fun onError(e: Throwable) {
        val base = Base()
        base.desc = e.message
        if (e is SocketTimeoutException) {
            base.desc = "网络连接超时"
        } else if (e is ParseException) {
            base.desc = "数据解析错误"
        } else if (e is HttpException) {
            base.desc = "请求失败"
        } else if (e is ConnectException) {
            base.code = Base.CONNECTION_ERROR_CODE
            base.desc = "网络异常，请检查网络"
            //        } else if (e instanceof JsonSyntaxException) {
//            //com.google.gson.JsonSyntaxException: java.lang.IllegalStateException: Expected BEGIN_OBJECT but was STRING at line 1 column 38 path $.data
//            // base.setCode(401);
        } else {
            if (TextUtils.isEmpty(base.desc)) {
                base.code = Base.UNKNOW_ERROR_CODE
                base.desc = "获取数据失败"
            }
        }
        try {
            baseView.error(requestCode, base)
        } catch (ignored: Exception) {
        }
        if (isShowLoading) baseView.finishLoading()
    }

    fun cancel() {
        isCanceled = true
        if (curDisposable != null && !curDisposable!!.isDisposed) {
            curDisposable!!.dispose()
            if (isShowLoading) {
                baseView.finishLoading()
            }
            try {
                val base = Base()
                //            String msg = "请求已取消";
//            base.setCode(CANCEL_CODE);
//            base.setDesc(msg);
                baseView.error(requestCode, base)
            } catch (ignored: Exception) {
            }
        }
    }

    override fun onComplete() {
        if (isShowLoading) baseView.finishLoading()
    }

    override fun onNext(t: T) {
        try {
            if (isShowLoading) {
                baseView.finishLoading()
            }
            if (t != null && t.code == 200) {
                baseView.success(requestCode, t)
            } else {
                baseView.error(requestCode, t)
            }
            curDisposable = null //销毁
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}