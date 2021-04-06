package com.boot.yuntechlife.util;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * @Author: skwen
 * @ClassName: Util
 * @Description: util接口
 * @Date: 2020-03-08
 */

public interface Util {
    String getLocalIp(HttpServletRequest request);

    Date getNowTimeDate() throws ParseException;

    String getRandomStr(int type, int length, String exChars);

    String getMd5(String str);

    String StrToBase64(String str);

    String Base64ToStr(String str);

    String getClientBrowser(HttpServletRequest request);

    String getClientSystem(HttpServletRequest request);

    Map<String, Object> getIpAddressesByIp(String ip) throws UnsupportedEncodingException;

    int getJsonInt(String json, String key);

    float getJsonFloat(String json, String key);

    Boolean getJsonBoolean(String json, String key);

    String getJsonStr(String json, String key);

    Date getJsonDate(String json, String key);

    String getStringMiddle(String text, String left, String right);
}
