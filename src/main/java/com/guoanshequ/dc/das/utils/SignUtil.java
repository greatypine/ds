package com.guoanshequ.dc.das.utils;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.utils
 * @Description:
 * @Author: gbl
 * @CreateDate: 2018/12/25 9:23
 */
public class SignUtil {

    /**
     * @Description 签名
     * @author gbl
     * @date 2018/12/20 15:04
     **/

    public static String generateSign(Map<String, Object> data, String appSecret) {
        String[] keyArray = new String[data.size()];
        int i=0;
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String key = entry.getKey();
            if (key == "sign") {
                continue;
            }
            keyArray[i] = key;
            i++;
        }
        keyArray = bubbleSort(keyArray);
        String stringA = "";
        String connector = "";
        for (String key:keyArray) {

            if (data.get(key) == null || data.get(key).toString().length() == 0) {
                continue;
            }
            stringA += connector + key + "=" + data.get(key);
            connector = "&";
        }
        stringA += connector + "appSecret=" + appSecret;
        System.out.println(stringA);

        return getMD5Str(stringA).toUpperCase();
    }


    public static String[] bubbleSort(String[] array) {
        if (array.length == 0)
            return array;
        for (int i = 0, l = array.length; i < l - 1; i++) {
            for (int j = 0; j < l - 1 - i; j++) {
                if (compare(array[j], array[j + 1]) == 1) {
                    String temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
        return array;
    }


    public static int compare(String o1, String o2) {
        //2者取最短
        int length = Math.min(o1.length(), o2.length());
        for (int i = 0; i < length; i++) {
            if (o1.charAt(i) < o2.charAt(i)) {
                return -1;
            } else if (o1.charAt(i) > o2.charAt(i)) {
                return 1;
            }
        }
        //程序运行到这 说明前面2者比较的部分是相等的，则继续比较长度
        if (o1.length() < o2.length()) {
            return -1;
        } else if (o1.length() > o2.length()) {
            return 1;
        }else{
            return 0;
        }
    }


    /**
     * @Description md5加密
     * @author gbl
     * @date 2018/12/20 15:04
     **/

    private static String getMD5Str(String content){
        String result = null;
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7',
                '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(content.getBytes("UTF-8"));
            //The result should be one 128 integer
            byte temp[] = md.digest();
            char str[] = new char[16 * 2];
            int k = 0;
            for (int i = 0; i < 16; i++) {
                byte byte0 = temp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            result = new String(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
