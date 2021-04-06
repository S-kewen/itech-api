package com.boot.yuntechlife.controller.user;

import com.boot.yuntechlife.annotation.LoginToken;
import com.boot.yuntechlife.entity.home.Console;
import com.boot.yuntechlife.entity.main.Token;
import com.boot.yuntechlife.entity.user.*;
import com.boot.yuntechlife.service.home.ConsoleInfoService;
import com.boot.yuntechlife.service.token.TokenService;
import com.boot.yuntechlife.service.user.*;
import com.boot.yuntechlife.util.Util;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: skwen
 * @ClassName: UserIndexController
 * @Description: 个人中心controller
 * @Date: 2020-03-09
 */
@RestController
@RequestMapping("/api")
public class UserIndexController {
    @Autowired
    private UserIndexService userIndexService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private BindingLineService bindingLineService;
    @Autowired
    private Util util;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private NewsService newsService;
    @Autowired
    private LoginRecordService loginRecordService;

    @LoginToken
    @RequestMapping("getUserIndexInfo")
    public Map<String, Object> getUserIndexInfo() {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            User user = new User();
            user.setId(token.getId());
            UserIndex userIndex = new UserIndex();
            userIndex = userIndexService.getByUserId(user);
            BindingLine bindingLine = new BindingLine();
            bindingLine.setUser_id(token.getId());
            bindingLine=bindingLineService.getInfo(bindingLine);
            if (userIndex != null) {
                map.put("status", 0);
                map.put("tip", "success");
                map.put("todayIncome", userIndex.getTodayIncome());
                map.put("processing", userIndex.getProcessing());
                map.put("pending", userIndex.getPending());
                map.put("totalOrders", userIndex.getTotalOrders());
                map.put("alldayIncome", userIndex.getAlldayIncome());
                map.put("todayFlow", userIndex.getTodayFlow());
                map.put("allFlow", userIndex.getAllFlow());
                map.put("warnNum", userIndex.getWarnNum());
                map.put("actualName", userIndex.getActual_name());
                map.put("balance", userIndex.getBalance());
                map.put("departmentId", userIndex.getDepartment_id());
                map.put("line", userIndex.getLine());
                map.put("verifiedState", userIndex.getVerified_state());
                map.put("className", userIndex.getClass_name());
                map.put("email", userIndex.getEmail());
                map.put("phone", userIndex.getPhone());
                if (bindingLine!=null){
                    if (bindingLine.getState()==1){
                        map.put("bindingLine",1);
                    }else{
                        map.put("bindingLine",2);
                    }
                }else{
                    map.put("bindingLine",0);
                }
            } else {
                map.put("status", -2);
                map.put("tip", "查詢用戶數據失敗,請稍候再試");
            }

        } else {
            map.put("status", -1);
            map.put("tip", "登錄已失效，請重新登錄");
        }

        return map;
    }

    @LoginToken
    @RequestMapping("getUserIndexMessage")
    public Map<String, Object> getUserIndexMessage() {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            User user = new User();
            user.setId(token.getId());
            PageHelper.startPage(1, 50);
            List<News> select = newsService.getList(user);
            PageInfo<News> pageInfo = new PageInfo<>(select);
            map.put("status", 0);
            map.put("tip", "success");
            map.put("total", pageInfo.getTotal());
            map.put("list", pageInfo.getList());
        } else {
            map.put("status", -1);
            map.put("tip", "登錄已失效，請重新登錄");
        }
        return map;
    }

    @LoginToken
    @RequestMapping("getUserIndexLoginRecord")
    public Map<String, Object> getUserIndexLoginRecord() {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            User user = new User();
            user.setId(token.getId());
            PageHelper.startPage(1, 50);
            List<LoginRecord> select = loginRecordService.getList(user);
            PageInfo<LoginRecord> pageInfo = new PageInfo<>(select);
            map.put("status", 0);
            map.put("tip", "success");
            map.put("total", pageInfo.getTotal());
            map.put("list", pageInfo.getList());
        } else {
            map.put("status", -1);
            map.put("tip", "登錄已失效，請重新登錄");
        }
        return map;
    }
}
