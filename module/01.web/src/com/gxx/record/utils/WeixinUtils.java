package com.gxx.record.utils;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;

/**
 * 微信工具类
 * User: Gxx
 * Time: 2014-02-20 16:09
 */
public class WeixinUtils
{
    /**
     * SHA1校验签名
     * @return
     */
    public static boolean checkSign(String token, String timestamp, String nonce, String expectSignature)
    {
        //放到一个数组
        String[] array = {token, timestamp, nonce};
        //字母排序
        Arrays.sort(array);
        //组织字符串
        String str = StringUtils.EMPTY;
        for(String temp : array){
            str += temp;
        }
        //SHA1加密
        String digest = new SHA1().getDigestOfString(str.getBytes()).toLowerCase();
        //比较是否一致
        return StringUtils.equals(expectSignature, digest);
    }
}
