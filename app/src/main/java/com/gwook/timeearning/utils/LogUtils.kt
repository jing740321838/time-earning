package com.gwook.timeearning.utils

import android.util.Log
import com.gwook.timeearning.BuildConfig

/**
 * 日志打印工具
 */
object LogUtils {
    //    private static Boolean openLog = true;
    var openLog = BuildConfig.DEBUG

    /**
     * log.i
     */
    fun i(tag: String?, content: String?) {
        if (openLog) {
            Log.i(tag, content!!)
        }
    }

    /**
     * log.d
     */
    fun d(tag: String?, content: String?) {
        if (openLog) {
            show(tag, content)
        }
    }

    private fun show(tag: String?, msg: String?) {  //信息太长,分段打印
        var s = msg
        if (tag == null || tag.isEmpty() || s == null || s.isEmpty()) return
        //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
        //  把4*1024的MAX字节打印长度改为2001字符数
        val maxLength = 3 * 1024
        //大于4000时
        while (s!!.length > maxLength) {
            Log.i(tag, s.substring(0, maxLength))
            s = s.substring(maxLength)
        }
        //剩余部分
        Log.i(tag, s)
    }

    /**
     * log.v
     */
    fun v(tag: String?, content: String?) {
        if (openLog) {
            Log.v(tag, content!!)
        }
    }

    /**
     * log.e
     */
    fun e(tag: String?, content: String?) {
        if (openLog) {
            Log.e(tag, content!!)
        }
    }

    /**
     * log.e
     */
    fun e(tag: String?, content: String?, tr: Throwable?) {
        if (openLog) {
            Log.e(tag, content, tr)
        }
    }
}