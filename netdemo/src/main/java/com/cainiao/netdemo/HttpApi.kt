package com.cainiao.netdemo

import okhttp3.Callback


/**
 * 网络请求的统一接口类
 */
interface HttpApi {
    /**
     * 抽象的http的get的请求封装，异步
     */
    fun get(params:Map<String,Any>,path:String,callback: IHttpCallback){}

    /**
     * 抽象的http同步的get请求
     */
    fun getSync(params:Map<String,Any>,path:String):Any?{
        return Any()
    }

    /**
     * 抽象的http的post的请求，异步
     */
    fun post(body:Any,path: String,callback: IHttpCallback){}

    fun postSync(body: Any,path: String):Any ?= Any()

    fun cancelRequest(tag : Any)

    fun cancelAllRequest()

}