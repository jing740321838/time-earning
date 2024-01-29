package com.gwook.timeearning.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * 对SharedPreference封装工具类
 */
object SharedPreferencesUtil {
    private const val SHARED_NAME = "shared_date"
    private var sharedPreferences: SharedPreferences? = null

    //存入String类型数据
    fun saveStringData(context: Context, key: String?, value: String?) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)
        }
        sharedPreferences!!.edit().putString(key, value).apply()
    }

    //取出String类型数据
    fun getStringData(context: Context, key: String?, defValue: String?): String? {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)
        }
        return sharedPreferences!!.getString(key, defValue)
    }
}