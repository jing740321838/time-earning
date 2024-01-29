package com.gwook.timeearning.api

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.gwook.timeearning.bean.Base
import com.gwook.timeearning.utils.LogUtils.i
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.IOException
import java.lang.reflect.Type

/**
 * Created by Administrator on 2018/2/2 0002.
 */
class MyGsonResponseBodyConverter<T : Base?> internal constructor(
    private val gson: Gson,
    private val type: Type
) : Converter<ResponseBody, T?> {
    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T? {
        val json = value.string()
        i("LogRul", json)
        return try {
            val jsonObject = JsonParser().parse(json).asJsonObject
            // 解析code
            val jsonPrimitive = jsonObject.getAsJsonPrimitive("code")
            var code = 0
            if (jsonPrimitive != null) {
                code = jsonPrimitive.asInt
            }
            // 解析message
            val jsonElement = jsonObject["msg"]
            var message: String? = null
            if (jsonElement != null) {
                message = jsonElement.asString
            }
            val t: T
            // 通过反射获取泛型的实例对象
            val clazz = type as Class<T>
            t = clazz.newInstance()
            if (code != 200) {
                val base = gson.fromJson(json, Base::class.java)
                // 解析message
                if (jsonElement != null) {
                    t!!.msg = message
                    base.msg = message
                }
                base.code = code
                t!!.msg = message
                base as T
                //            // 按停服公告的格式解析，封装到notify字段中
//            try {
//                JsonElement element = jsonObject.get("data");
//                // t.notify = gson.fromJson(jsonObject.getAsJsonObject("data"), Notify.class);
//                if(!TextUtils.isEmpty(element.getAsString())){
////                    t.notify = gson.fromJson(element.getAsString(), Notify.class);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            } else {
                // 按标准格式解析
                try {
                    gson.fromJson(json, type)
                } catch (e: JsonSyntaxException) {
                    val base = gson.fromJson(json, Base::class.java)
                    base.code = code
                    val jsonElement2 = jsonObject["msg"]
                    t!!.msg = message
                    base.msg = jsonElement2.asString
                    base as T
                }
            }
            //        return t;
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}