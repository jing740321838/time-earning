package com.gwook.timeearning.api

import com.gwook.timeearning.utils.LogUtils
import okhttp3.logging.HttpLoggingInterceptor

class HttpLogger : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        LogUtils.i("HttpLogInfo", message)
    }
}