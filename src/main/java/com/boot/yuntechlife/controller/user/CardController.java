package com.boot.yuntechlife.controller.user;

import com.boot.yuntechlife.annotation.LoginToken;
import com.boot.yuntechlife.entity.main.Token;
import com.boot.yuntechlife.entity.user.*;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: skwen
 * @ClassName: CardController
 * @Description: controller
 * @Date: 2020-03-14
 */
@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://courier.iskwen.com", maxAge = 3600)
public class CardController {
    @Autowired
    private UserService userService;
    @Autowired
    private CardService cardService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private RechargeRecordService rechargeRecordService;
    @Autowired
    private Util util;
    @Autowired
    private HttpServletRequest request;

    @LoginToken
    @RequestMapping("recharge")
    public Map<String, Object> deleteLoginRecord(String username, String cdkey) {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            User user = new User();
            user.setUsername(username);
            user = userService.getByUsername(user);
            if (user != null) {
                Card card = new Card();
                card.setCdkey(cdkey);
                card = cardService.getByCdkey(card);
                if (card != null) {
                    if (card.getVaild_time().after(new Date())) {
                        if (card.getState() == 1) {
                            RechargeRecord rechargeRecord = new RechargeRecord();
                            rechargeRecord.setUser_id(user.getId());
                            rechargeRecord.setState(1);
                            rechargeRecord.setType(1);
                            rechargeRecord.setCard_id(card.getId());
                            int count = rechargeRecordService.insertOne(rechargeRecord);
                            if (count > 0) {
                                if (user.getId() != token.getId()) {
                                    News news = new News();
                                    news.setUser_id(user.getId());
                                    news.setState(1);
                                    news.setType(1);
                                    news.setSender("系統消息");
                                    news.setTitle("儲值中心");
                                    news.setMsg(token.getUsername() + "為您儲值了" + card.getAmount() + "元，趕快去感謝他吧~");
                                    newsService.insertOne(news);
                                }
                                map.put("status", 0);
                                map.put("tip", "success");
                            } else {
                                map.put("status", -7);
                                map.put("tip", "儲值失敗,請稍候再試");
                            }
                        } else if (card.getState() == 2) {
                            map.put("status", -4);
                            map.put("tip", "該卡密已被使用");
                        } else if (card.getState() == 3) {
                            map.put("status", -5);
                            map.put("tip", "該卡密已被凍結");
                        }
                    } else {
                        map.put("status", -6);
                        map.put("tip", "該卡密已過期");
                    }
                } else {
                    map.put("status", -3);
                    map.put("tip", "該卡密不存在");
                }
            } else {
                map.put("status", -2);
                map.put("tip", "該用戶不存在,請檢查用戶名");
            }
        } else {
            map.put("status", -1);
            map.put("tip", "登錄已失效，請重新登錄");
        }

        return map;
    }

}
