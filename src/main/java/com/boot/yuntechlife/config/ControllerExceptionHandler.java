package com.boot.yuntechlife.config;

import com.alibaba.fastjson.JSONObject;
import com.boot.yuntechlife.entity.system.SystemLog;
import com.boot.yuntechlife.service.system.SystemLogService;
import com.boot.yuntechlife.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;

@ControllerAdvice

public class ControllerExceptionHandler {
    @Autowired
    private Util util;
    @Value("${performance.serviceId.client}")
    private int serviceId;
    @Autowired
    private SystemLogService systemLogService;
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object defaultErrorHandler(HttpServletRequest request, Exception e) {
        JSONObject json = new JSONObject();
        SystemLog systemLog = new SystemLog();
        String hostname = "unknown";
        String ip = "unknown";
        json.put("tip", e.getMessage());
        json.put("timestamp", System.currentTimeMillis());
        json.put("ip", util.getLocalIp(request));
        json.put("query", request.getQueryString());
        json.put("method", request.getMethod());
        json.put("contextPath", request.getContextPath());
        json.put("cookies", request.getCookies());
        json.put("header", request.getHeader("user-agent"));
        json.put("authType", request.getAuthType());
        json.put("query", request.getQueryString());
        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            systemLog.setType(2);
            json.put("status", -404);
        } else {
            systemLog.setType(3);
            json.put("status", -500);
        }
        try {
            hostname = InetAddress.getLocalHost().getHostName();
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (java.net.UnknownHostException ex) {
            json.put("exception",ex.toString());
        }
        systemLog.setState(2);
        systemLog.setService_id(serviceId);
        systemLog.setPc_name(hostname);
        systemLog.setIp(ip);
        systemLog.setMsg(json.toString());
        systemLog.setRemark("ControllerExceptionHandler");
        if (systemLogService.insertOne(systemLog)==0){
            System.out.println("ControllerExceptionHandler fail!!!");
        }
        return json;
    }
}