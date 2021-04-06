package com.boot.yuntechlife.util.impl;

import com.boot.yuntechlife.entity.system.BaiduStatistics;
import com.boot.yuntechlife.util.BaiduCloudApiUtil;
import com.boot.yuntechlife.util.EmailUtil;
import com.boot.yuntechlife.util.HttpUtil;
import com.boot.yuntechlife.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author: skwen
 * @ClassName: BaiduCloudApiUtilImpl
 * @Description: 百度雲工具類
 * @Date: 2020-03-08
 */
@Component
public class BaiduCloudApiUtilImpl implements BaiduCloudApiUtil {
    @Value("${baiduStatistics.username}")
    private String username;
    @Value("${baiduStatistics.password}")
    private String password;
    @Value("${baiduStatistics.token}")
    private String token;
    @Value("${baiduStatistics.siteId}")
    private String siteId;
    @Autowired
    private Util util;
    @Autowired
    private HttpUtil httpUtil;

    @Override
    public BaiduStatistics getStatisticsInfo() throws ParseException {
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        JSONObject content = new JSONObject();
        JSONObject header = new JSONObject();
        header.put("username", username);
        header.put("password", password);
        header.put("token", token);
        header.put("account_type", "1");
        content.put("header", header);
        JSONObject body = new JSONObject();
        body.put("site_id", siteId);
        body.put("method", "source/all/a");
        body.put("start_date", date);
        body.put("end_date", date);
        body.put("metrics",
                "pv_count,pv_ratio,visit_count,visitor_count,new_visitor_count,new_visitor_ratio,ip_count,bounce_ratio,avg_visit_time,avg_visit_pages");
        body.put("max_results", 0);
        body.put("gran", "day");
        content.put("body", body);
        String url = "https://api.baidu.com/json/tongji/v1/ReportService/getData";
        String result = sendHttpRequest(url, content.toJSONString());
        result = util.getStringMiddle(result, "]],[[", "]],[],[]],");
        BaiduStatistics baiduStatistics = new BaiduStatistics();
        baiduStatistics.setSite_id(siteId);
        baiduStatistics.setDate(new SimpleDateFormat("yyyyMMdd").parse(date));
        baiduStatistics.setPv_count(0);
        baiduStatistics.setPv_ratio(0.0F);
        baiduStatistics.setVisit_count(0);
        baiduStatistics.setVisitor_count(0);
        baiduStatistics.setNew_visitor_count(0);
        baiduStatistics.setNew_visitor_ratio(0.0F);
        baiduStatistics.setIp_count(0);
        baiduStatistics.setBounce_ratio(0.0F);
        baiduStatistics.setAvg_visit_time(0);
        baiduStatistics.setAvg_visit_pages(0.0F);
        baiduStatistics.setDeleted(false);
        baiduStatistics.setAdd_time(new Date());
        if (result != "") {
            String[] strArr = result.split(",");
            if (strArr.length >= 10) {
                if (isInteger(strArr[0])) {
                    if (strArr[0] != "" && !strArr[0].equals("\"--\""))
                        baiduStatistics.setPv_count(Integer.parseInt(strArr[0]));
                    if (strArr[1] != "" && !strArr[1].equals("\"--\""))
                        baiduStatistics.setPv_ratio(Float.parseFloat(strArr[1]));
                    if (strArr[2] != "" && !strArr[2].equals("\"--\""))
                        baiduStatistics.setVisit_count(Integer.parseInt(strArr[2]));
                    if (strArr[3] != "" && !strArr[3].equals("\"--\""))
                        baiduStatistics.setVisitor_count(Integer.parseInt(strArr[3]));
                    if (strArr[4] != "" && !strArr[4].equals("\"--\""))
                        baiduStatistics.setNew_visitor_count(Integer.parseInt(strArr[4]));
                    if (strArr[5] != "" && !strArr[5].equals("\"--\""))
                        baiduStatistics.setNew_visitor_ratio(Float.parseFloat(strArr[5]));
                    if (strArr[6] != "" && !strArr[6].equals("\"--\""))
                        baiduStatistics.setIp_count(Integer.parseInt(strArr[6]));
                    if (strArr[7] != "" && !strArr[7].equals("\"--\""))
                        baiduStatistics.setBounce_ratio(Float.parseFloat(strArr[7]));
                    if (strArr[8] != "" && !strArr[8].equals("\"--\""))
                        baiduStatistics.setAvg_visit_time(Integer.parseInt(strArr[8]));
                    if (strArr[9] != "" && !strArr[9].equals("\"--\""))
                        baiduStatistics.setAvg_visit_pages(Float.parseFloat(strArr[9].replace("]", "")));
                }
            }
        }
        return baiduStatistics;
    }

    public String sendHttpRequest(String url, String content) {
        URLConnection connection;
        try {
            connection = new java.net.URL(url).openConnection();
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Length", "" + content.length());
            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.setRequestProperty("Content-Type", "application/json");
            DataOutputStream stream = new DataOutputStream(connection.getOutputStream());
            stream.write(content.getBytes("UTF-8"));
            stream.close();
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            StringBuffer sb = new StringBuffer();
            String str = br.readLine();
            while (str != null) {
                sb.append(str);
                str = br.readLine();
            }
            br.close();
            return sb.toString();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return "";
    }

    public boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}
