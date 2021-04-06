package com.boot.yuntechlife.util.impl;

import com.boot.yuntechlife.util.TencentCloudApiUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import com.tencentcloudapi.captcha.v20190722.CaptchaClient;
import com.tencentcloudapi.captcha.v20190722.models.DescribeCaptchaResultRequest;
import com.tencentcloudapi.captcha.v20190722.models.DescribeCaptchaResultResponse;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: skwen
 * @ClassName: TencentCloudApiImpl
 * @Description: 騰訊雲api工具類
 * @Date: 2020-03-08
 */
@Component
public class TencentCloudApiUtilImpl implements TencentCloudApiUtil {
    @Value("${tencentCloud.captcha.appId}")
    private String captchaAppId;
    @Value("${tencentCloud.captcha.secretKey}")
    private String appSecretKey;
    @Value("${tencentCloud.secretId}")
    private String secretId;
    @Value("${tencentCloud.secretKey}")
    private String secretKey;
    @Value("${tencentCloud.cos.region}")
    private String cosRegion;
    @Value("${tencentCloud.cos.bucketName}")
    private String bucketName;
    @Value("${tencentCloud.cos.url}")
    private String url;

    class CaptchaResponse {
        private String RequestId;
        private int CaptchaCode;
        private String CaptchaMsg;
        private int EvilLevel;

        public String getRequestId() {
            return RequestId;
        }

        public void setRequestId(String requestId) {
            RequestId = requestId;
        }

        public int getCaptchaCode() {
            return CaptchaCode;
        }

        public void setCaptchaCode(int captchaCode) {
            CaptchaCode = captchaCode;
        }

        public String getCaptchaMsg() {
            return CaptchaMsg;
        }

        public void setCaptchaMsg(String captchaMsg) {
            CaptchaMsg = captchaMsg;
        }

        public int getEvilLevel() {
            return EvilLevel;
        }

        public void setEvilLevel(int evilLevel) {
            EvilLevel = evilLevel;
        }

        public CaptchaResponse(String requestId, int captchaCode, String captchaMsg, int evilLevel) {
            super();
            RequestId = requestId;
            CaptchaCode = captchaCode;
            CaptchaMsg = captchaMsg;
            EvilLevel = evilLevel;
        }

        public CaptchaResponse() {
            super();
        }

        @Override
        public String toString() {
            return "CaptchaResponse [RequestId=" + RequestId + ", CaptchaCode=" + CaptchaCode + ", CaptchaMsg="
                    + CaptchaMsg + ", EvilLevel=" + EvilLevel + "]";
        }


    }

    @Override
    public boolean codeResponse(String userIp, String ticket, String randstr) {
        String str = "";
        try {
            Credential cred = new Credential(secretId, secretKey);
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("captcha.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            CaptchaClient client = new CaptchaClient(cred, "ap-guangzhou", clientProfile);

            String params = "{\"CaptchaType\":9,\"Ticket\":\"" + ticket + "\",\"UserIp\":\"" + userIp
                    + "\",\"Randstr\":\"" + randstr + "\",\"" + "CaptchaAppId\":" + captchaAppId
                    + ",\"AppSecretKey\":\"" + appSecretKey + "\"}";

            DescribeCaptchaResultRequest req = DescribeCaptchaResultRequest.fromJsonString(params,
                    DescribeCaptchaResultRequest.class);
            DescribeCaptchaResultResponse resp = client.DescribeCaptchaResult(req);

            str = DescribeCaptchaResultRequest.toJsonString(resp);

        } catch (TencentCloudSDKException e) {

            e.toString();
        }
        if (str.indexOf("OK") >= 0) {
            return true;
        } else {
            return false;
        }
        //return CaptchaResponse;
    }

    @Override
    public Map<String, Object> cosUpload(File fileItem, String key) throws Exception {
        Map<String, Object> map = new HashMap<>();
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region(cosRegion);
        ClientConfig clientConfig = new ClientConfig(region);
        COSClient cosClient = new COSClient(cred, clientConfig);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, fileItem);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        if (putObjectResult.getContentMd5() != null && putObjectResult.getContentMd5() != "") {
            map.put("status", 0);
            map.put("tip", "success");
            map.put("url", url + "/" + key);
        } else {
            map.put("status", -1);
            map.put("tip", "unknown");
        }
        return map;
    }

    @Override
    public boolean deleteTempFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                return file.delete();
            }
        }
        return false;
    }
}
