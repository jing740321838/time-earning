package com.gwook.timeearning.api

import com.gwook.timeearning.bean.Base
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface CommonApiService {
    /********************************************** 登录 **********************************************/
//    /**
//     *  发送验证码
//     */
//    @POST("app/verificationCode")
//    fun verificationCode(@Query("mobileNo") mobileNo: String): Observable<MobileBean>
//
//    /**
//     * 登录  登录方式 1货主物流公司登录 2 司机端登录
//     */
//    @POST("app/queryUserlist")
//    fun queryUserlist(
//        @Query("userPhone") userPhone: String,
//        @Query("password") password: String,
//        @Query("type") type: String,
//        @Query("cid") cid: String
//    ): Observable<LoginBean>
//
//    /**
//     *  忘记密码
//     */
//    @POST("app/forgetPassword")
//    fun forgetPassword(@Query("mobileNo") mobileNo: String): Observable<MobileBean>

    /**
     *	修改密码
     */
    @GET("app/updatePassWord")
    fun updatePassWord(
        @Query("oldPassword") oldPassword: String,
        @Query("newPassword") newPassword: String,
        @Query("userPhone") userPhone: String
    ): Observable<Base>

}