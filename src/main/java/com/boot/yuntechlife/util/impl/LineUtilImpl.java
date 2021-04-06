package com.boot.yuntechlife.util.impl;

import com.boot.yuntechlife.entity.line.LineNews;
import com.boot.yuntechlife.service.user.UserService;
import com.boot.yuntechlife.util.HttpUtil;
import com.boot.yuntechlife.util.LineUtil;
import com.boot.yuntechlife.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.apache.http.HttpEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Author: skwen
 * @ClassName: LineUtilImpl
 * @Description: 工具類
 * @Date: 2020-03-21
 */
@Component
public class LineUtilImpl implements LineUtil {
    @Value("${yunCourier.appId}")
    private int appId;
    @Value("${yunCourier.token}")
    private String token;
    @Value("${yunCourier.lineConfigId}")
    private int lineConfigId;
    @Autowired
    private Util util;
    @Autowired
    private HttpUtil httpUtil;
    @Override
    public boolean pushMessage(String to, String type, String text){
        String url="https://courier-api.iskwen.com/api/pushMessage?applyId=" + appId + "&token=" + token + "&lineConfigId="+lineConfigId+"&to=" + to +"&type="+type+ "&text=" + text;
        String result = httpUtil.get(url, null);
        if (result != null && result != "") {
            if (util.getJsonInt(result, "status") == 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
