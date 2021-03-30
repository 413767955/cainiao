package com.cainiao.netdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val map:Map<String,String> = mapOf(
            "key" to "free",
            "appid" to "0",
            "msg" to "你好啊"
        )
        val httpApi:HttpApi = OkHttpApi()
        httpApi.get(map,"api.php",object :IHttpCallback{
            override fun onSuccess(data: Any?) {
                //TODO("Not yet implemented")
                LogUtils.e("${data.toString()}")
                runOnUiThread {
                    tv_hello.text = data.toString()
                }
            }
            override fun onFailed(error: Any?) {
                //TODO("Not yet implemented")
                LogUtils.e("${error.toString()}")
            }

        })
    }
}