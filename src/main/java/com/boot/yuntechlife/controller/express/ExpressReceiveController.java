package com.boot.yuntechlife.controller.express;

import com.boot.yuntechlife.annotation.LoginToken;
import com.boot.yuntechlife.entity.express.ExpressIntegral;
import com.boot.yuntechlife.entity.express.ExpressReceive;
import com.boot.yuntechlife.entity.express.ExpressTaker;
import com.boot.yuntechlife.entity.main.Token;
import com.boot.yuntechlife.entity.user.User;
import com.boot.yuntechlife.service.express.ExpressIntegralService;
import com.boot.yuntechlife.service.express.ExpressReceiveService;
import com.boot.yuntechlife.service.express.ExpressTakerService;
import com.boot.yuntechlife.service.token.TokenService;
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
 * @ClassName: ExpressReceiveController
 * @Description: controller
 * @Date: 2020-03-16
 */
@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://courier.iskwen.com", maxAge = 3600)
public class ExpressReceiveController {
    @Value("${expressReceive.dayLimit}")
    private int dayLimit;
    @Value("${expressReceive.PendingLimit}")
    private int pendingLimit;
    @Autowired
    private UserService userService;
    @Autowired
    private ExpressReceiveService expressReceiveService;
    @Autowired
    private ExpressTakerService expressTakerService;
    @Autowired
    private ExpressIntegralService expressIntegralService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private Util util;
    @Autowired
    private HttpServletRequest request;

    @LoginToken
    @RequestMapping("listExpressTakerReceive")
    public Map<String, Object> listExpressTakerReceive(int pageNumber, int pageSize, String sortName, String sortOrder, String keyword, int state, String startTime, String endTime) throws ParseException {
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
            List<Map<String, Object>> select = expressReceiveService.getList(user);
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

    @LoginToken
    @RequestMapping("deleteExpressReceive")
    public Map<String, Object> deleteExpressReceive(int id) {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            ExpressReceive expressReceive = new ExpressReceive();
            expressReceive.setUser_id(token.getId());
            expressReceive.setId(id);
            expressReceive = expressReceiveService.getById(expressReceive);
            if (expressReceive != null) {
                if (expressReceive.getState() == 5 || expressReceive.getState() == 6 || expressReceive.getState() == 9) {
                    int count = expressReceiveService.deleteOne(expressReceive);
                    if (count > 0) {
                        map.put("status", 0);
                        map.put("tip", "success");
                    } else {
                        map.put("status", -4);
                        map.put("tip", "刪除失敗,請稍候再試");
                    }
                } else {
                    map.put("status", -3);
                    map.put("tip", "該訂單目前不允許刪除");
                }
            } else {
                map.put("status", -2);
                map.put("tip", "該訂單不存在");
            }
        } else {
            map.put("status", -1);
            map.put("tip", "登錄已失效，請重新登錄");
        }

        return map;
    }

    @LoginToken
    @RequestMapping("changeExpressReceiveState")
    public Map<String, Object> changeExpressReceiveState(int id, int state) {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            ExpressReceive expressReceive = new ExpressReceive();
            expressReceive.setUser_id(token.getId());
            expressReceive.setId(id);
            expressReceive = expressReceiveService.getById(expressReceive);
            if (expressReceive != null) {
                if (state == 6) {//取消訂單
                    if (expressReceive.getState() == 2) {
                        expressReceive.setState(state);
                        int count = expressReceiveService.updateOne(expressReceive);
                        if (count > 0) {
                            map.put("status", 0);
                            map.put("tip", "success");
                        } else {
                            map.put("status", -4);
                            map.put("tip", "取消訂單失敗,請稍後再試");
                        }
                    } else {
                        map.put("status", -3);
                        map.put("tip", "當前狀態不允許取消訂單");
                    }
                } else if (state == 3) {//發貨
                    if (expressReceive.getState() == 2) {
                        expressReceive.setState(state);
                        int count = expressReceiveService.updateOne(expressReceive);
                        if (count > 0) {
                            map.put("status", 0);
                            map.put("tip", "success");
                        } else {
                            map.put("status", -5);
                            map.put("tip", "發貨失敗,請稍後再試");
                        }
                    } else {
                        map.put("status", -6);
                        map.put("tip", "當前狀態不允許發貨");
                    }
                } else {
                    map.put("status", -7);
                    map.put("tip", "非法請求");
                }
            } else {
                map.put("status", -2);
                map.put("tip", "該訂單不存在");
            }
        } else {
            map.put("status", -1);
            map.put("tip", "登錄已失效，請重新登錄");
        }
        return map;
    }

    @LoginToken
    @RequestMapping("addExpressReceive")
    public Map<String, Object> addExpressReceive(int id, String line, String phone) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            User user = new User();
            user.setId(token.getId());
            user = userService.getById(user);
            if (user != null) {
                if (user.getVerified_state() == 3) {
                    ExpressTaker expressTaker = new ExpressTaker();
                    expressTaker.setId(id);
                    expressTaker = expressTakerService.getById(expressTaker);
                    if (expressTaker != null) {
                        if (expressTaker.getState() == 1) {
                            ExpressIntegral expressIntegral = new ExpressIntegral();
                            expressIntegral.setUser_id(token.getId());
                            expressIntegral = expressIntegralService.getByUserId(expressIntegral);
                            if (expressIntegral != null) {
                                if (expressIntegral.getState() == 1) {
                                    ExpressReceive expressReceive = new ExpressReceive();
                                    expressReceive.setUser_id(token.getId());
                                    expressReceive.setAdd_time(new Date());
                                    int count = expressReceiveService.getCount(expressReceive);
                                    if (!(count >= dayLimit)) {
                                        count = expressReceiveService.getCountByPending(expressReceive);
                                        if (!(count >= pendingLimit)) {
                                            if (!(expressTaker.getUser_id() == token.getId())) {
                                                expressReceive.setState(2);
                                                expressReceive.setExpress_taker_id(id);
                                                expressReceive.setReal_name(user.getActual_name());
                                                expressReceive.setLine(line);
                                                expressReceive.setPhone(phone);
                                                count = expressReceiveService.insertOne(expressReceive);
                                                if (count > 0) {
                                                    map.put("status", 0);
                                                    map.put("tip", "success");
                                                } else {
                                                    map.put("status", -10);
                                                    map.put("tip", "接單失敗,請稍後再試");
                                                }
                                            } else {
                                                map.put("status", -13);
                                                map.put("tip", "不能接自己的訂單哦");
                                            }
                                        } else {
                                            map.put("status", -11);
                                            map.put("tip", "您還有" + count + "個訂單等待處理,趕快去處理吧");
                                        }
                                    } else {
                                        map.put("status", -12);
                                        map.put("tip", "一天只能接" + dayLimit + "單哦~");
                                    }
                                } else {
                                    map.put("status", -15);
                                    map.put("tip", "系統凍結了您的接單權限,請聯繫客服處理");
                                }
                            } else {
                                map.put("status", -14);
                                map.put("tip", "您暫時不允許接單,請聯繫客服處理");
                            }
                        } else {
                            map.put("status", -9);
                            map.put("tip", "該訂單目前暫時不允許被接單");
                        }
                    } else {
                        map.put("status", -8);
                        map.put("tip", "該訂單不存在");
                    }
                } else {
                    map.put("status", -7);
                    map.put("tip", "您還沒有通過身份認證,無法進行接單");
                }
            } else {
                map.put("status", -3);
                map.put("tip", "登錄已失效，請重新登錄");
            }
        } else {
            map.put("status", -1);
            map.put("tip", "登錄已失效，請重新登錄");
        }
        return map;
    }

    @LoginToken
    @RequestMapping("getExpressTakerInfoByReceive")
    public Map<String, Object> getExpressTakerInfoByReceive(int id) {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            ExpressReceive expressReceive = new ExpressReceive();
            expressReceive.setExpress_taker_id(id);
            expressReceive.setUser_id(token.getId());
            expressReceive = expressReceiveService.getByExpressTakerId(expressReceive);
            if (expressReceive != null) {
                ExpressTaker expressTaker = new ExpressTaker();
                expressTaker.setId(id);
                expressTaker = expressTakerService.getByReceive(expressTaker);
                if (expressTaker != null) {
                    if (!(expressReceive.getState() == 6)) {
                        map.put("status", 0);
                        map.put("tip", "success");
                        map.put("expressName", expressTaker.getExpress_name());
                        map.put("expressNum", expressTaker.getExpress_num());
                        map.put("recipientName", expressTaker.getRecipient_name());
                        map.put("recipientPhone", expressTaker.getRecipient_phone());
                        map.put("expressType", expressTaker.getExpress_type());
                        map.put("takePoints", expressTaker.getTake_points());
                        map.put("contactName", expressTaker.getContact_name());
                        map.put("appointmentPoints", expressTaker.getAppointment_points());
                        map.put("phone", expressTaker.getPhone());
                        map.put("line", expressTaker.getLine());
                        map.put("appointmentTime", expressTaker.getAppointment_time());
                        map.put("remark", expressTaker.getRemark());
                    } else {
                        map.put("status", -4);
                        map.put("tip", "該訂單不存在");
                    }
                } else {
                    map.put("status", -3);
                    map.put("tip", "該訂單不存在");
                }
            } else {
                map.put("status", -2);
                map.put("tip", "該訂單不存在");
            }
        } else {
            map.put("status", -1);
            map.put("tip", "登錄已失效，請重新登錄");
        }
        return map;
    }

}
