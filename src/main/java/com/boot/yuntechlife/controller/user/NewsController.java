package com.boot.yuntechlife.controller.user;

import com.boot.yuntechlife.annotation.LoginToken;
import com.boot.yuntechlife.entity.*;
import com.boot.yuntechlife.entity.main.Token;
import com.boot.yuntechlife.entity.user.News;
import com.boot.yuntechlife.entity.user.User;
import com.boot.yuntechlife.service.*;
import com.boot.yuntechlife.service.token.TokenService;
import com.boot.yuntechlife.service.user.LoginRecordService;
import com.boot.yuntechlife.service.user.NewsService;
import com.boot.yuntechlife.service.user.UserService;
import com.boot.yuntechlife.util.Util;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @ClassName: NewsController
 * @Description: 系統消息controller
 * @Date: 2020-03-08
 */
@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://courier.iskwen.com", maxAge = 3600)
public class NewsController {
    @Autowired
    private UserService userService;
    @Autowired
    private LoginRecordService loginRecordService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private Util util;
    @Autowired
    private HttpServletRequest request;

    @LoginToken
    @RequestMapping("listMessage")
    public Map<String, Object> listMessage(int pageNumber, int pageSize, String sortName, String sortOrder, String keyword, int state, String startTime, String endTime) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            User user = new User();
            user.setId(token.getId());
            user.setKeyword(keyword);
            user.setState(state);
            if (startTime != null && startTime != "") {
                user.setStartTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime));
            }
            if (endTime != null && endTime != "") {
                user.setEndTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime));
            }
            String order = "";
            if (sortName != "" && sortName != "") {
                order = sortName;
                if (sortOrder != "" && sortOrder != "") {
                    order += " " + sortOrder;
                }
            }
            PageHelper.startPage(pageNumber, pageSize, order);
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
    @RequestMapping("deleteMessage")
    public Map<String, Object> deleteMessage(int id) {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            News news = new News();
            news.setUser_id(token.getId());
            news.setId(id);
            int count = newsService.deleteById(news);
            if (count > 0) {
                map.put("status", 0);
                map.put("tip", "success");
            } else {
                map.put("status", -7);
                map.put("tip", "刪除失敗,請稍候再試");
            }

        } else {
            map.put("status", -1);
            map.put("tip", "登錄已失效，請重新登錄");
        }

        return map;
    }

    @LoginToken
    @RequestMapping("readMessage")
    public Map<String, Object> readMessage(int id) {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            News news = new News();
            news.setUser_id(token.getId());
            news.setId(id);
            news.setState(2);
            int count = newsService.updateById(news);
            if (count > 0) {
                map.put("status", 0);
                map.put("tip", "success");
            } else {
                map.put("status", -7);
                map.put("tip", "設置失敗,請稍候再試");
            }

        } else {
            map.put("status", -1);
            map.put("tip", "登錄已失效，請重新登錄");
        }

        return map;
    }

    @LoginToken
    @RequestMapping("allReadMessage")
    public Map<String, Object> allReadMessage() {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            News news = new News();
            news.setUser_id(token.getId());
            news.setState(2);
            int count = newsService.allRead(news);
            if (count > 0) {
                map.put("status", 0);
                map.put("tip", "success");
                map.put("count", count);
            } else {
                map.put("status", -2);
                map.put("tip", "暫時沒有未讀的消息");
            }

        } else {
            map.put("status", -1);
            map.put("tip", "登錄已失效，請重新登錄");
        }

        return map;
    }

    @LoginToken
    @RequestMapping("getUnreadMessageCount")
    public Map<String, Object> getUnreadMessageCount() {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            News news = new News();
            news.setUser_id(token.getId());
            news.setState(1);
            int count = newsService.getCount(news);
            map.put("status", 0);
            map.put("tip", "success");
            map.put("unreadMessageCount", count);
        } else {
            map.put("status", -1);
            map.put("tip", "登錄已失效，請重新登錄");
        }

        return map;
    }

    @LoginToken
    @RequestMapping("listUserIndexMessage")
    public Map<String, Object> listUserIndexMessage() {
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

}
