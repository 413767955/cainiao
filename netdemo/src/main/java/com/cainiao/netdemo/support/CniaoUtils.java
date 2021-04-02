package com.cainiao.netdemo.support;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.EncryptUtils;
import com.cainiao.netdemo.config.CniaoConfigKt;

public class CniaoUtils {
    private CniaoUtils() {
    }

    public static String unicodeEncode(String string){
        return string;
    }

    public static String unicodeDecode(String string){
        return string;
    }
    @Nullable
    public static String decodeData(@Nullable String dataStr){
        if (dataStr != null){
            return new String(EncryptUtils.decryptBase64AES(
                    dataStr.getBytes(),
                    CniaoConfigKt.ENT_CONFIG_APPKEY.getBytes(),
                    "" + "AES/CBC/PKCS7Padding", "J#y9sJesy*5HmqLq".getBytes()));
        }else {
            return null;
        }
    }
}
