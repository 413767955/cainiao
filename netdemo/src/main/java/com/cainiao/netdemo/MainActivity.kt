package com.cainiao.netdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.cainiao.netdemo.model.NetResponse
import com.cainiao.netdemo.support.CniaoUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      /*  val map:Map<String,String> = mapOf(
            "key" to "free",
            "appid" to "0",
            "msg" to "你好啊"
        )*/
        //val httpApi:HttpApi = OkHttpApi()
        httpApi.get(emptyMap(),"https://course.api.cniao5.com/member/userinfo",object :IHttpCallback{
            override fun onSuccess(data: Any?) {
                //TODO("Not yet implemented")
                LogUtils.d("${data.toString()}")
                runOnUiThread {
                    tv_hello.text = data.toString()
                }
            }
            override fun onFailed(error: Any?) {
                //TODO("Not yet implemented")
                LogUtils.d("${error.toString()}")
            }

        })
        httpApi.post(loginBody,"https://course.api.cniao5.com/accounts/course/10301/login",object : IHttpCallback{
            override fun onSuccess(data: Any?) {
                //TODO("Not yet implemented")
                LogUtils.e("${data.toString()}")
                runOnUiThread {
                    tv_post.text = "post"+data.toString()
                    val toString = data.toString()
                    val (code,dataObj,message) = GsonUtils.fromJson<NetResponse>(
                        toString,
                        NetResponse::class.java
                    )
                    tv_post.text = CniaoUtils.decodeData(dataObj.toString())
                }
            }

            override fun onFailed(error: Any?) {
                //TODO("Not yet implemented")
                LogUtils.e("${error.toString()}")
            }
        })
    }
    val loginBody = LoginReq()
    val httpApi : HttpApi = OkHttpApi()
    data class LoginReq(
        val mobi :String = "18648957777",
        val password :String = "cn5123456"
    )

}