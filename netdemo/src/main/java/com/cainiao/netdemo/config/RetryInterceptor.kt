package com.cainiao.netdemo.config

import com.blankj.utilcode.util.LogUtils
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * @Description: 重试请求的网络拦截器
 * @Author: lyl
 * @Date: 2021/3/31 16:26
 */
class RetryInterceptor(private val maxRetry:Int = 0 ) :Interceptor {
    private var retriedNum :Int = 0 //已经重试的次数，注意，设置maxRetry重试次数，作用于重试，所以总的请求次数，可能就是原始的1 + maxRetry

    override fun intercept(chain: Interceptor.Chain): Response {
        //TODO("Not yet implemented")
        val request : Request = chain.request()
        LogUtils.e("当前retriedNum = $retriedNum")
        var response :Response = chain.proceed(request)
        while (!response.isSuccessful && retriedNum < maxRetry){
            retriedNum ++
            LogUtils.e("-----当前retriedNum = $retriedNum")
            response = chain.proceed(request)
        }
        return response
    }

}