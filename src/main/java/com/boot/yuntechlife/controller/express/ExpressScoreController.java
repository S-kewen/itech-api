package com.boot.yuntechlife.controller.express;

import com.boot.yuntechlife.annotation.LoginToken;
import com.boot.yuntechlife.entity.express.ExpressIntegral;
import com.boot.yuntechlife.entity.express.ExpressReceive;
import com.boot.yuntechlife.entity.express.ExpressScore;
import com.boot.yuntechlife.entity.express.ExpressTaker;
import com.boot.yuntechlife.entity.main.Token;
import com.boot.yuntechlife.entity.user.User;
import com.boot.yuntechlife.service.express.ExpressIntegralService;
import com.boot.yuntechlife.service.express.ExpressReceiveService;
import com.boot.yuntechlife.service.express.ExpressScoreService;
import com.boot.yuntechlife.service.express.ExpressTakerService;
import com.boot.yuntechlife.service.token.TokenService;
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
 * @ClassName: ExpressScoreController
 * @Description: controller
 * @Date: 2020-03-16
 */
@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://courier.iskwen.com", maxAge = 3600)
public class ExpressScoreController {
    @Autowired
    private UserService userService;
    @Autowired
    private ExpressTakerService expressTakerService;
    @Autowired
    private ExpressReceiveService expressReceiveService;
    @Autowired
    private ExpressScoreService expressScoreService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private Util util;
    @Autowired
    private HttpServletRequest request;

    @LoginToken
    @RequestMapping("addExpressScore")
    public Map<String, Object> addExpressScore(int id, int anonymous, int type, int score, String comments) {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            if (score <= 0) {
                map.put("status", -5);
                map.put("tip", "非法請求");
                return map;
            } else if (score > 5) {
                map.put("status", -6);
                map.put("tip", "非法請求");
                return map;
            }
            if (type == 1) {//接單者==>發單者
                ExpressTaker expressTaker = new ExpressTaker();
                expressTaker.setId(id);
                expressTaker = expressTakerService.getByReceive(expressTaker);
                if (expressTaker != null) {
                    ExpressReceive expressReceive = new ExpressReceive();
                    expressReceive.setUser_id(token.getId());
                    expressReceive.setExpress_taker_id(id);
                    expressReceive = expressReceiveService.getByExpressTakerId(expressReceive);
                    if (expressReceive != null) {
                        if (expressReceive.getState() == 4) {
                            ExpressScore expressScore = new ExpressScore();
                            expressScore.setUser_id(expressTaker.getUser_id());
                            expressScore.setFrom_user_id(token.getId());
                            expressScore.setExpress_taker_id(expressReceive.getExpress_taker_id());
                            expressScore.setState(1);
                            expressScore.setType(type);
                            expressScore.setAnonymous(anonymous);
                            expressScore.setScore(score);
                            expressScore.setComments(comments);
                            int count = expressScoreService.insertOne(expressScore);
                            if (count > 0) {
                                map.put("status", 0);
                                map.put("tip", "success");
                            } else {
                                map.put("status", -7);
                                map.put("tip", "評價失敗,請稍後再試");
                            }
                        } else {
                            map.put("status", -4);
                            map.put("tip", "該訂單目前不允許評價");
                        }
                    } else {
                        map.put("status", -3);
                        map.put("tip", "該訂單不存在");
                    }
                } else {
                    map.put("status", -2);
                    map.put("tip", "該訂單不存在");
                }
            } else if (type == 2) {//發單者==>接單者
                ExpressTaker expressTaker = new ExpressTaker();
                expressTaker.setId(id);
                expressTaker.setUser_id(token.getId());
                expressTaker = expressTakerService.getById(expressTaker);
                if (expressTaker != null) {
                    ExpressReceive expressReceive = new ExpressReceive();
                    expressReceive.setExpress_taker_id(id);
                    expressReceive = expressReceiveService.getByIssuance(expressReceive);
                    if (expressReceive != null) {
                        if (expressReceive.getState() == 4) {
                            ExpressScore expressScore = new ExpressScore();
                            expressScore.setUser_id(expressReceive.getUser_id());
                            expressScore.setFrom_user_id(token.getId());
                            expressScore.setExpress_taker_id(expressTaker.getId());
                            expressScore.setState(1);
                            expressScore.setType(type);
                            expressScore.setAnonymous(anonymous);
                            expressScore.setScore(score);
                            expressScore.setComments(comments);
                            int count = expressScoreService.insertOne(expressScore);
                            if (count > 0) {
                                map.put("status", 0);
                                map.put("tip", "success");
                            } else {
                                map.put("status", -7);
                                map.put("tip", "評價失敗,請稍後再試");
                            }
                        } else {
                            map.put("status", -4);
                            map.put("tip", "該訂單目前不允許評價");
                        }
                    } else {
                        map.put("status", -3);
                        map.put("tip", "該訂單不存在");
                    }
                } else {
                    map.put("status", -2);
                    map.put("tip", "該訂單不存在");
                }
            }
        } else {
            map.put("status", -1);
            map.put("tip", "登錄已失效，請重新登錄");
        }
        return map;
    }
}
