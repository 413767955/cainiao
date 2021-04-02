package com.cainiao.netdemo.model

/**
 * @Description: 基础的网络返回数据结构
 * @Author: lyl
 * @Date: 2021/4/2 11:58
 */
 data class NetResponse(
    val code: Int,
    val data:Any?,
    val message: String

)