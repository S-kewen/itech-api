package com.boot.yuntechlife.util.impl;

import com.boot.yuntechlife.util.EmailUtil;
import com.boot.yuntechlife.util.HttpUtil;
import com.boot.yuntechlife.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * @Author: skwen
 * @ClassName: EmailUtilImpl
 * @Description: email工具類
 * @Date: 2020-03-08
 */
@Component
public class EmailUtilImpl implements EmailUtil {
    @Value("${yunCourier.appId}")
    private int appId;
    @Value("${yunCourier.token}")
    private String token;
    @Autowired
    private Util util;
    @Autowired
    private HttpUtil httpUtil;

    @Override
    public boolean sendEasyMail(String to, String title, String msg) {
        String result = httpUtil.get("https://courier-api.iskwen.com/api/easyMail?applyId=" + appId + "&token=" + token + "&to=" + to + "&title=" + title + "&msg=" + msg, null);
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

    @Override
    public boolean sendYuntechFlowWarn(String to, String title, String ip, double insUp, double insDown, double extUp, double extDown, double flow, double ratio, double warnValue) {
        DecimalFormat df = new DecimalFormat("######0.00");
        String result = httpUtil.get("https://courier-api.iskwen.com/api/yuntechFlowWarn?applyId=" + appId + "&token=" + token + "&to=" + to + "&title=" + title + "&ip=" + ip + "&insUp=" + df.format(insUp) + "&insDown=" + df.format(insDown) + "&extUp=" + df.format(extUp) + "&extDown=" + df.format(extDown) + "&flow=" + df.format(flow) + "&ratio=" + df.format(ratio) + "&warnValue=" + df.format(warnValue), null);
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
