package com.boot.yuntechlife.controller.user;

import com.alibaba.druid.sql.visitor.functions.Bin;
import com.boot.yuntechlife.annotation.LoginToken;
import com.boot.yuntechlife.annotation.PassToken;
import com.boot.yuntechlife.entity.main.Token;
import com.boot.yuntechlife.entity.user.*;
import com.boot.yuntechlife.service.token.TokenService;
import com.boot.yuntechlife.service.user.*;
import com.boot.yuntechlife.util.TencentCloudApiUtil;
import com.boot.yuntechlife.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: skwen
 * @ClassName: BindingLineController
 * @Description: controller
 * @Date: 2020-03-24
 */
@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://courier.iskwen.com", maxAge = 3600)
public class BindingLineController {
    @Autowired
    private UserService userService;
    @Autowired
    private CardService cardService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private BindingLineService bindingLineService;
    @Autowired
    private TencentCloudApiUtil tencentCloudApiUtil;
    @Autowired
    private Util util;
    @Autowired
    private HttpServletRequest request;

    @PassToken
    @RequestMapping("bindingLine")
    public Map<String, Object> bindingLine(String username, String password, String vall, String randstr, String language, String viewType, String userId, String utouId, String roomId, String groupId) {
        Map<String, Object> map = new HashMap<>();
        if (!tencentCloudApiUtil.codeResponse(util.getLocalIp(request), vall, randstr)) {
            map.put("status", -1);
            map.put("tip", "驗證碼錯誤");
            return map;
        }
        if (userId == null || userId == "") {
            map.put("status", -8);
            map.put("tip", "非法請求");
            return map;
        }
        if (language == null) {
            language = "unknown";
        }
        if (viewType == null) {
            viewType = "unknown";
        }
        if (utouId == null) {
            utouId = "unknown";
        }
        if (roomId == null) {
            roomId = "unknown";
        }
        if (groupId == null) {
            groupId = "unknown";
        }
        User user = new User();
        user.setUsername(username);
        user = userService.getByUsername(user);
        if (user == null) {
            map.put("status", -2);
            map.put("tip", "用戶名或密碼錯誤");//用戶不存在
        } else {//用戶存在
            user.setPassword(password);
            user = userService.userLogin(user);
            if (user == null) {//密碼錯誤
                map.put("status", -2);
                map.put("tip", "用戶名或密碼錯誤");
            } else {
                if (user.getState() == 1) {
                    BindingLine bindingLine = new BindingLine();
                    bindingLine.setUser_id(user.getId());
                    bindingLine.setState(1);
                    int count = bindingLineService.getCount(bindingLine);
                    if (count > 0) {
                        map.put("status", -9);
                        map.put("tip", "該賬號已綁定,無需重複綁定");
                        return map;
                    }
                    bindingLine.setType(1);
                    bindingLine.setLanguage(language);
                    bindingLine.setView_type(viewType);
                    bindingLine.setLine_user_id(userId);
                    bindingLine.setUtou_id(utouId);
                    bindingLine.setRoom_id(roomId);
                    bindingLine.setGroup_id(groupId);
                    bindingLine.setIp(util.getLocalIp(request));
                    bindingLine.setSystem(util.getClientSystem(request));
                    bindingLine.setBrowser(util.getClientBrowser(request));
                    bindingLine.setHeader(request.getHeader("user-agent"));
                    count = bindingLineService.insertOne(bindingLine);
                    if (count > 0) {
                        map.put("status", 0);
                        map.put("tip", "success");
                    } else {
                        map.put("status", -9);
                        map.put("tip", "綁定失敗,請稍後再試");
                    }
                } else if (user.getState() == 2) {
                    map.put("status", -3);
                    map.put("tip", "該賬號被永久封禁");
                } else if (user.getState() == 3) {
                    map.put("status", -4);
                    map.put("tip", "該賬號已被封禁,解封時間:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(user.getRelease_time()));
                } else if (user.getState() == 4) {
                    map.put("status", -5);
                    map.put("tip", "該賬號已被安全凍結,請聯繫客服處理");
                } else if (user.getState() == 5) {
                    map.put("status", -7);
                    map.put("tip", "該賬號還沒激活,請前往郵箱激活~");
                } else {
                    map.put("status", -6);
                    map.put("tip", "該賬號狀態異常,請聯繫客服處理");
                }
            }
        }
        return map;
    }

    @LoginToken
    @RequestMapping("cancelBindingLine")
    public Map<String, Object> cancelBindingLine() {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            BindingLine bindingLine = new BindingLine();
            bindingLine.setUser_id(token.getId());
            int count = bindingLineService.deleteOne(bindingLine);
            if (count > 0) {
                map.put("status", 0);
                map.put("tip", "success");
            } else {
                map.put("status", -2);
                map.put("tip", "解綁失敗,請稍候再試");
            }

        } else {
            map.put("status", -1);
            map.put("tip", "登錄已失效，請重新登錄");
        }

        return map;
    }

    @PassToken
    @RequestMapping("getBindingLineInfoByLine")
    public Map<String, Object> getBindingLineInfoByLine(String lineUserId) {
        Map<String, Object> map = new HashMap<>();
        BindingLine bindingLine = new BindingLine();
        bindingLine.setLine_user_id(lineUserId);
        bindingLine = bindingLineService.getInfoByLine(bindingLine);
        if (bindingLine == null) {
            map.put("status", 0);
            map.put("tip", "success");
        } else {
            map.put("status", -2);
            map.put("tip", "該Line已被綁定,請勿重複綁定");
        }
        return map;
    }
}
