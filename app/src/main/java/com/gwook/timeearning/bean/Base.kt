package com.gwook.timeearning.bean

class Base {
    var code = 0
    var msg: String? = null
    var desc: String? = null //不是接口返回的数据，是发生一些异常时，自己捕捉的描述

    companion object {
        const val UNKNOW_ERROR_CODE = 9901
        const val CONNECTION_ERROR_CODE = 9902
        const val CANCEL_CODE = 9903
    }
}