package com.boot.yuntechlife.controller.system;

import com.boot.yuntechlife.annotation.LoginToken;
import com.boot.yuntechlife.entity.main.Token;
import com.boot.yuntechlife.entity.system.Feedback;
import com.boot.yuntechlife.entity.user.LoginRecord;
import com.boot.yuntechlife.entity.user.User;
import com.boot.yuntechlife.service.system.FeedbackService;
import com.boot.yuntechlife.service.token.TokenService;
import com.boot.yuntechlife.service.user.LoginRecordService;
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
 * @ClassName: FeedbackController
 * @Description: controller
 * @Date: 2020-03-15
 */
@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://courier.iskwen.com", maxAge = 3600)
public class FeedbackController {
    @Value("${feedback.dayLimit}")
    private int dayLimit;
    @Autowired
    private UserService userService;
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private Util util;
    @Autowired
    private HttpServletRequest request;

    @LoginToken
    @RequestMapping("addFeedback")
    public Map<String, Object> addFeedback(int type, int anonymous, String content) {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            Feedback feedback = new Feedback();
            feedback.setUser_id(token.getId());
            feedback.setState(1);
            feedback.setAdd_time(new Date());
            int count = feedbackService.getCount(feedback);
            if (!(count >= dayLimit)) {
                feedback.setType(type);
                feedback.setAnonymous(anonymous);
                feedback.setContent(content);
                count = feedbackService.insertOne(feedback);
                if (count > 0) {
                    map.put("status", 0);
                    map.put("tip", "success");
                } else {
                    map.put("status", -2);
                    map.put("tip", "提交失敗,請稍後再試");
                }

            } else {
                map.put("status", -3);
                map.put("tip", "每天只能反饋" + dayLimit + "次哦~");
            }

        } else {
            map.put("status", -1);
            map.put("tip", "登錄已失效，請重新登錄");
        }

        return map;
    }

}
