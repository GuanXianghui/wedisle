package com.gxx.record.utils;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;

/**
 * ΢�Ź�����
 * User: Gxx
 * Time: 2014-02-20 16:09
 */
public class WeixinUtils
{
    /**
     * SHA1У��ǩ��
     * @return
     */
    public static boolean checkSign(String token, String timestamp, String nonce, String expectSignature)
    {
        //�ŵ�һ������
        String[] array = {token, timestamp, nonce};
        //��ĸ����
        Arrays.sort(array);
        //��֯�ַ���
        String str = StringUtils.EMPTY;
        for(String temp : array){
            str += temp;
        }
        //SHA1����
        String digest = new SHA1().getDigestOfString(str.getBytes()).toLowerCase();
        //�Ƚ��Ƿ�һ��
        return StringUtils.equals(expectSignature, digest);
    }
}
