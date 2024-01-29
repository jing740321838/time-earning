package com.gwook.timeearning

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.chad.library.adapter.base.module.LoadMoreModuleConfig.defLoadMoreView
import com.gwook.timeearning.loadmore.CustomLoadMoreView
import com.wanjian.cockroach.Cockroach
import com.wanjian.cockroach.ExceptionHandler

class MyApplication : Application() {
    var activities: MutableList<Activity> = ArrayList()
    override fun onCreate() {
        super.onCreate()
        instance = this
        initCrash()
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                activities.add(activity)
            }

            override fun onActivityStarted(activity: Activity) {}
            override fun onActivityResumed(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {
                activities.remove(activity)
            }
        })
        defLoadMoreView = CustomLoadMoreView()
    }

    fun finishActivity(activity: Activity?) {
        var activity = activity
        if (activity != null) {
            activities.remove(activity)
            activity.finish()
            activity = null
        }
    }

    /**
     * 防crash
     */
    private fun initCrash() {
        val sysExceptHandler = Thread.getDefaultUncaughtExceptionHandler()
        Cockroach.install(context, object : ExceptionHandler() {
            override fun onUncaughtExceptionHappened(thread: Thread, throwable: Throwable) {
                //捕获到导致崩溃的异常
                Handler(Looper.getMainLooper()).post {}
            }

            override fun onBandageExceptionHappened(throwable: Throwable) {
                throwable.printStackTrace() //打印警告级别log，该throwable可能是最开始的bug导致的，无需关心
            }

            override fun onEnterSafeMode() {
                //进入安全模式
            }

            override fun onMayBeBlackScreen(e: Throwable) {
                val thread = Looper.getMainLooper().thread
                //黑屏时建议直接杀死app
                sysExceptHandler?.uncaughtException(thread, RuntimeException("black screen"))
            }
        })
    }

    companion object {
        private var instance: MyApplication? = null
        fun getInstance(): MyApplication {
            return if (instance == null) MyApplication() else instance!!
        }

        @JvmStatic
        val context: Context
            get() = instance!!.applicationContext

        fun finishActivities() {
            for (activity in getInstance().activities) {
                try {
                    activity.finish()
                } catch (ignored: Exception) {
                }
            }
        }
    }
}