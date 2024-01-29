package com.gwook.timeearning.base

import com.gwook.timeearning.bean.Base

interface BaseView<T : Base?> {
    fun loading()
    fun finishLoading()
    fun error(requestCode: Int, t: Base?) // 根据请求码做不同的错误处理
    fun success(requestCode: Int, t: Base)
}