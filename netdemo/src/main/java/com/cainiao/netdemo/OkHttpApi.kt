package com.cainiao.netdemo

import androidx.collection.SimpleArrayMap
import com.cainiao.netdemo.config.CnInterceptor
import com.cainiao.netdemo.config.KtHttpLogInterceptor
import com.cainiao.netdemo.config.LocalCookieJar
import com.cainiao.netdemo.config.RetryInterceptor
import com.google.gson.Gson
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

class OkHttpApi :HttpApi {
    companion object{
        private const val TAG = "OkHttpApi"
    }
    private var baseUrl = "http://api.qingyunke.com/"
    var maxRetry : Int = 0

    //存储请求，用于取消
    private val callMap = SimpleArrayMap<Any,Call>()

    private val mClient: OkHttpClient = OkHttpClient.Builder()
        .callTimeout(10, TimeUnit.SECONDS) //完整请求超时时长，从发起到接收返回数据，默认值0，不限定
        .connectTimeout(10,TimeUnit.SECONDS)//与服务器建立连接的时长，默认10s
        .readTimeout(10,TimeUnit.SECONDS)//读取服务器返回数据的时长
        .writeTimeout(10,TimeUnit.SECONDS)//向服务器写入数据的时长，默认10s
        .retryOnConnectionFailure(true)//重连
        .followRedirects(false)//重定向
        .cache(Cache(File("sdcard/cache","okhttp"),1024))
        .cookieJar(LocalCookieJar())
        .addNetworkInterceptor(CnInterceptor())
        .addNetworkInterceptor(KtHttpLogInterceptor{
            logLevel(KtHttpLogInterceptor.LogLevel.BODY)
        })//添加网络拦截器，可以对okhttp的网络请求做拦截处理，不同于应用拦截器，这里能感知所有网络状态，比如重定向
        .addNetworkInterceptor(RetryInterceptor(maxRetry))
        .build()


    override fun get(params: Map<String, Any>, path: String, callback: IHttpCallback) {
        val url = "$baseUrl$path"
        val urlBuilder:HttpUrl.Builder = url.toHttpUrl().newBuilder()
        params.forEach{entry ->
            urlBuilder.addEncodedQueryParameter(entry.key,entry.value.toString())
        }
        val request = Request.Builder()
            .get()
            .tag(params)
            .url(urlBuilder.build())
            .cacheControl(CacheControl.FORCE_NETWORK)
            .build()
        val newCall = mClient.newCall(request)
        callMap.put(request.tag(),newCall)
        newCall.enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                //TODO("Not yet implemented")
                callback.onFailed(e.message)
            }
            override fun onResponse(call: Call, response: Response) {
                //TODO("Not yet implemented")
                callback.onSuccess(response.body?.string())
            }
        })
    }

    override fun post(body: Any, path: String, callback: IHttpCallback) {
        //super.post(body, path, callback)
        val url = "$baseUrl$path"
        val request:Request = Request.Builder()
            .post(Gson().toJson(body).toRequestBody())
            .url("https://testapi.cniao5.com/account/login")
            .tag(body)
            .build()

        val newCall = mClient.newCall(request)
        callMap.put(request.tag(),newCall)
        newCall.enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                //TODO("Not yet implemented")
                callback.onFailed(e.message)
            }
            override fun onResponse(call: Call, response: Response) {
                //TODO("Not yet implemented")
                callback.onSuccess(response.body?.toString())
            }
        })
    }

    override fun cancelRequest(tag: Any) {
        callMap.get(tag)?.cancel()
    }

    override fun cancelAllRequest() {
        for ( i in 0 until callMap.size()){
            callMap.get(callMap.keyAt(i))?.cancel()
        }
    }
}

