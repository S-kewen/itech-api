package com.boot.yuntechlife.controller.home;

import com.boot.yuntechlife.annotation.LoginToken;
import com.boot.yuntechlife.entity.flow.YuntechFlow;
import com.boot.yuntechlife.entity.flow.YuntechFlowSet;
import com.boot.yuntechlife.entity.home.Console;
import com.boot.yuntechlife.entity.main.Token;
import com.boot.yuntechlife.entity.system.BaiduStatistics;
import com.boot.yuntechlife.entity.user.LoginRecord;
import com.boot.yuntechlife.entity.user.User;
import com.boot.yuntechlife.service.home.ConsoleInfoService;
import com.boot.yuntechlife.service.system.BaiduStatisticsService;
import com.boot.yuntechlife.service.token.TokenService;
import com.boot.yuntechlife.service.user.LoginRecordService;
import com.boot.yuntechlife.service.user.UserService;
import com.boot.yuntechlife.util.Util;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: skwen
 * @ClassName: ConsoleInfoController
 * @Description: 控制台屬性controller
 * @Date: 2020-03-09
 */
@RestController
@RequestMapping("/api")
public class ConsoleController {
    @Value("${baiduStatistics.siteId}")
    private String siteId;
    @Autowired
    private UserService userService;
    @Autowired
    private BaiduStatisticsService baiduStatisticsService;
    @Autowired
    private LoginRecordService loginRecordService;
    @Autowired
    private ConsoleInfoService consoleInfoService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private Util util;
    @Autowired
    private HttpServletRequest request;

    @LoginToken
    @RequestMapping("getConsoleInfo")
    public Map<String, Object> getConsoleInfo() {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            User user = new User();
            user.setId(token.getId());
            Console select = new Console();
            select = consoleInfoService.getByUserId(user);
            if (select != null) {
                map.put("status", 0);
                map.put("tip", "success");
                map.put("todayFlow", select.getTodayFlow());
                map.put("todayIncome", select.getTodayIncome());
                map.put("unCompletedNum", select.getUnCompletedNum());
                map.put("completedNum", select.getCompletedNum());
                map.put("unExpressNum", select.getUnExpressNum());
                map.put("expressNum", select.getExpressNum());
                map.put("verifiedState", select.getVerifiedState());
            } else {
                map.put("status", -2);
                map.put("tip", "查詢控制台數據失敗");
            }

        } else {
            map.put("status", -1);
            map.put("tip", "登錄已失效，請重新登錄");
        }

        return map;
    }

    @LoginToken
    @RequestMapping("getEchartsInfoByStatisticalReport")
    public Map<String, Object> getEchartsInfoByHistory(String nowDate) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            if (nowDate == null || nowDate == "") {
                map.put("status", -2);
                map.put("tip", "參數缺失,非法請求");
                return map;
            }
            BaiduStatistics baiduStatistic = new BaiduStatistics();
            baiduStatistic.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(nowDate));
            baiduStatistic.setSite_id(siteId);
            PageHelper.startPage(1, 7);
            List<BaiduStatistics> select = baiduStatisticsService.getListByWeek(baiduStatistic);
            PageInfo<BaiduStatistics> pageInfo = new PageInfo<>(select);
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
    @RequestMapping("getEchartsInfoByLoginCount")
    public Map<String, Object> getEchartsInfoByCount(String nowDate) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            if (nowDate == null || nowDate == "") {
                map.put("status", -2);
                map.put("tip", "參數缺失,非法請求");
                return map;
            }
            LoginRecord loginRecord = new LoginRecord();
            loginRecord.setState(1);
            loginRecord.setAdd_time(new SimpleDateFormat("yyyy-MM-dd").parse(nowDate));
            PageHelper.startPage(1, 7);
            List<Map<String, Object>> select = loginRecordService.getCountByWeek(loginRecord);
            PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(select);
            map.put("status", 0);
            map.put("tip", "success");
            map.put("list", pageInfo.getList());
        } else {
            map.put("status", -1);
            map.put("tip", "登錄已失效，請重新登錄");
        }
        return map;
    }

    @LoginToken
    @RequestMapping("getEchartsInfoByUserCount")
    public Map<String, Object> getEchartsInfoByUserCount(String nowDate) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            if (nowDate == null || nowDate == "") {
                map.put("status", -2);
                map.put("tip", "參數缺失,非法請求");
                return map;
            }
            User user = new User();
            user.setAdd_time(new SimpleDateFormat("yyyy-MM-dd").parse(nowDate));
            PageHelper.startPage(1, 7);
            List<Map<String, Object>> select = userService.getCountByWeek(user);
            PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(select);
            map.put("status", 0);
            map.put("tip", "success");
            map.put("list", pageInfo.getList());
        } else {
            map.put("status", -1);
            map.put("tip", "登錄已失效，請重新登錄");
        }
        return map;
    }

}
