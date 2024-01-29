package com.gwook.timeearning.api

import android.text.TextUtils
import com.gwook.timeearning.MyApplication.Companion.context
import com.gwook.timeearning.utils.SharedPreferencesUtil.getStringData
import com.gwook.timeearning.api.NetWorkUrl.getBaseUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object HttpUtils {
    private const val DEFAULT_CONNECT_TIME = 15

    //    private static final int DEFAULT_WRITE_TIME = 25;
    private const val DEFAULT_READ_TIME = 25//连接超时时间

    //                    .writeTimeout(DEFAULT_WRITE_TIME, TimeUnit.SECONDS)//设置写操作超时时间
    //设置读操作超时时间
    //添加拦截器
    // .addNetworkInterceptor(logInterceptor)
    //  .addInterceptor(logRul)
    //            if (context instanceof Application) {
//                //loggingInterceptor.setLogOut(context);
//            }
    @JvmStatic
    val okHttp: OkHttpClient
        get() = try {
            val logInterceptor = HttpLoggingInterceptor(HttpLogger())
            val loggingInterceptor = LoggingInterceptor()
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY
            //            if (context instanceof Application) {
//                //loggingInterceptor.setLogOut(context);
//            }
            OkHttpClient().newBuilder()
                .connectTimeout(DEFAULT_CONNECT_TIME.toLong(), TimeUnit.SECONDS) //连接超时时间
                //                    .writeTimeout(DEFAULT_WRITE_TIME, TimeUnit.SECONDS)//设置写操作超时时间
                .readTimeout(DEFAULT_READ_TIME.toLong(), TimeUnit.SECONDS) //设置读操作超时时间
                .addInterceptor(logInterceptor) //添加拦截器
                .addInterceptor(loggingInterceptor) // .addNetworkInterceptor(logInterceptor)
                //  .addInterceptor(logRul)
                .build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    @JvmStatic
    fun getRetrofit(okHttpClient: OkHttpClient?, url: String?): Retrofit {
        return Retrofit.Builder().client(okHttpClient)
            .addConverterFactory(MyGsonConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl(if (TextUtils.isEmpty(url)) requestUrl else url)
            .build()
    }

    val requestUrl: String?
        get() = getStringData(
            context,
            NetWorkUrl.ENVIRONMENT, getBaseUrl()
        )
}