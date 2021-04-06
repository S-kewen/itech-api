package com.boot.yuntechlife.config;

import com.alibaba.fastjson.JSONObject;
import com.boot.yuntechlife.entity.system.SystemLog;
import com.boot.yuntechlife.service.system.SystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.net.InetAddress;

/**
 * @Author: skwen
 * @ClassName: ApplicationInit
 * @Description: 初始化
 * @Date: 2020-04-06
 */

@Component
@Order(value = 1000)
public class ApplicationInit implements ApplicationRunner {
    @Value("${http.port}")
    private int httpPort;
    @Value("${server.port}")
    private int port;
    @Value("${performance.serviceId.client}")
    private int serviceId;
    @Autowired
    private SystemLogService systemLogService;

    @Override
    public void run(ApplicationArguments args) throws Exception  {
        JSONObject json = new JSONObject();
        String hostname = "unknown";
        String ip = "unknown";
        try {
            hostname = InetAddress.getLocalHost().getHostName();
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (java.net.UnknownHostException ex) {
            System.out.println(ex.toString());
        }
        json.put("httpPort", httpPort);
        json.put("port", port);
        json.put("tip","success");
        json.put("status",0);
        json.put("callback","startUp");
        SystemLog systemLog = new SystemLog();
        systemLog.setState(1);
        systemLog.setType(1);
        systemLog.setService_id(serviceId);
        systemLog.setPc_name(hostname);
        systemLog.setIp(ip);
        systemLog.setMsg(json.toString());
        systemLog.setRemark("ApplicationInit");
        if (systemLogService.insertOne(systemLog)>0){
            System.out.println("ApplicationInit Successful!!!");
        }
    }
    @PreDestroy
    public void close() throws Exception {
        JSONObject json = new JSONObject();
        String hostname = "unknown";
        String ip = "unknown";
        try {
            hostname = InetAddress.getLocalHost().getHostName();
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (java.net.UnknownHostException ex) {
            System.out.println(ex.toString());
        }
        json.put("httpPort", httpPort);
        json.put("port", port);
        json.put("tip","success");
        json.put("status",0);
        json.put("callback","shutDown");
        SystemLog systemLog = new SystemLog();
        systemLog.setState(1);
        systemLog.setType(1);
        systemLog.setService_id(serviceId);
        systemLog.setPc_name(hostname);
        systemLog.setIp(ip);
        systemLog.setMsg(json.toString());
        systemLog.setRemark("ApplicationClose");
        if (systemLogService.insertOne(systemLog)>0){
            System.out.println("Close Successful!!!");
        }
    }
}
