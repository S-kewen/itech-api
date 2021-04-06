package com.boot.yuntechlife.controller.system;

import com.boot.yuntechlife.annotation.LoginToken;
import com.boot.yuntechlife.entity.main.Token;
import com.boot.yuntechlife.entity.system.Feedback;
import com.boot.yuntechlife.entity.system.MessageBoard;
import com.boot.yuntechlife.entity.user.LoginRecord;
import com.boot.yuntechlife.entity.user.User;
import com.boot.yuntechlife.service.system.FeedbackService;
import com.boot.yuntechlife.service.system.MessageBoardService;
import com.boot.yuntechlife.service.token.TokenService;
import com.boot.yuntechlife.service.user.NewsService;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: skwen
 * @ClassName: MessageBoardController
 * @Description: controller
 * @Date: 2020-03-15
 */
@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://courier.iskwen.com", maxAge = 3600)
public class MessageBoardController {
    @Value("${messageBoard.dayLimit}")
    private int dayLimit;
    @Autowired
    private UserService userService;
    @Autowired
    private MessageBoardService messageBoardService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private Util util;
    @Autowired
    private HttpServletRequest request;

    @LoginToken
    @RequestMapping("addMessageBoard")
    public Map<String, Object> addMessageBoard(int anonymous, String content) {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            MessageBoard messageBoard = new MessageBoard();
            messageBoard.setUser_id(token.getId());
            messageBoard.setAdd_time(new Date());
            int count = messageBoardService.getCount(messageBoard);
            if (!(count >= dayLimit)) {
                messageBoard.setState(1);
                messageBoard.setAnonymous(anonymous);
                messageBoard.setMsg(content);
                messageBoard.setIp(util.getLocalIp(request));
                messageBoard.setSystem(util.getClientSystem(request));
                messageBoard.setBrowser(util.getClientBrowser(request));
                messageBoard.setHeader(request.getHeader("user-agent"));
                count = messageBoardService.insertOne(messageBoard);
                if (count > 0) {
                    map.put("status", 0);
                    map.put("tip", "success");
                } else {
                    map.put("status", -2);
                    map.put("tip", "提交失敗,請稍後再試");
                }

            } else {
                map.put("status", -3);
                map.put("tip", "每天只能留言" + dayLimit + "次哦~");
            }

        } else {
            map.put("status", -1);
            map.put("tip", "登錄已失效，請重新登錄");
        }

        return map;
    }

    @LoginToken
    @RequestMapping("listMessageBoard")
    public Map<String, Object> listMessageBoard(int pageNumber, int pageSize) {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            MessageBoard messageBoard = new MessageBoard();
            PageHelper.startPage(pageNumber, pageSize);
            List<Map<String, Object>> select = messageBoardService.getList(messageBoard);
            PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(select);
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
