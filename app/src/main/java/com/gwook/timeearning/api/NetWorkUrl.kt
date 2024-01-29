package com.gwook.timeearning.api

import com.gwook.timeearning.BuildConfig

object NetWorkUrl {
    const val ENVIRONMENT = "environment"
    private const val URL_VALUE = BuildConfig.BUILD_TARGET//测试为1 生产为9

    private const val BASE_TEST = "https://xcx.zongmu.com.cn/"
    private const val BASE = "https://xcx.zongmu.com.cn/"

    const val URL_DOWN = "http://118.190.63.170:8181"//下载暂时的地址（pdf、安装包）

    @JvmStatic
    fun getBaseUrl(): String {
        when (URL_VALUE) {
            1 -> return BASE_TEST
            9 -> return BASE
        }
        return BASE
    }
}
