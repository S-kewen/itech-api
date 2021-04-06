package com.boot.yuntechlife.controller.express;

import com.boot.yuntechlife.annotation.LoginToken;
import com.boot.yuntechlife.entity.express.ExpressIntegral;
import com.boot.yuntechlife.entity.express.ExpressReceive;
import com.boot.yuntechlife.entity.express.ExpressTaker;
import com.boot.yuntechlife.entity.main.Token;
import com.boot.yuntechlife.entity.user.News;
import com.boot.yuntechlife.entity.user.User;
import com.boot.yuntechlife.service.express.ExpressIntegralService;
import com.boot.yuntechlife.service.express.ExpressReceiveService;
import com.boot.yuntechlife.service.express.ExpressTakerService;
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
 * @ClassName: ExpressTakerController
 * @Description: controller
 * @Date: 2020-03-15
 */
@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://courier.iskwen.com", maxAge = 3600)
public class ExpressTakerController {
    @Autowired
    private UserService userService;
    @Autowired
    private ExpressTakerService expressTakerService;
    @Autowired
    private ExpressReceiveService expressReceiveService;
    @Autowired
    private ExpressIntegralService expressIntegralService;

    @Autowired
    private TokenService tokenService;
    @Autowired
    private Util util;
    @Autowired
    private HttpServletRequest request;

    @LoginToken
    @RequestMapping("listExpressTakerIssuance")
    public Map<String, Object> listExpressTakerIssuance(int pageNumber, int pageSize, String sortName, String sortOrder, String keyword, int state, String startTime, String endTime) throws ParseException {
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
            List<ExpressTaker> select = expressTakerService.getList(user);
            PageInfo<ExpressTaker> pageInfo = new PageInfo<>(select);
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
    @RequestMapping("listExpressTaker")
    public Map<String, Object> listExpressTaker(int pageNumber, int pageSize, String sortName, String sortOrder, String expressName, int expressType, int takePoints, String startTime, String endTime) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            User user = new User();
            user.setState(1);
            user.setExpress_name(expressName);
            user.setExpress_type(expressType);
            user.setTake_points(takePoints);
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
            PageHelper.startPage(pageNumber, 100000, order);
            List<ExpressTaker> select = expressTakerService.getListByMarket(user);
            PageInfo<ExpressTaker> pageInfo = new PageInfo<>(select);
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
    @RequestMapping("deleteExpressTaker")
    public Map<String, Object> deleteExpressTaker(int id) {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            ExpressTaker expressTaker = new ExpressTaker();
            expressTaker.setUser_id(token.getId());
            expressTaker.setId(id);
            expressTaker = expressTakerService.getById(expressTaker);
            if (expressTaker != null) {
                if (expressTaker.getState() == 5 || expressTaker.getState() == 6 || expressTaker.getState() == 8 || expressTaker.getState() == 9) {
                    int count = expressTakerService.deleteOne(expressTaker);
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
    @RequestMapping("updateSurcharge")
    public Map<String, Object> updateSurcharge(int id, float surcharge) {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            ExpressTaker expressTaker = new ExpressTaker();
            expressTaker.setUser_id(token.getId());
            expressTaker.setId(id);
            expressTaker = expressTakerService.getById(expressTaker);
            if (expressTaker != null) {
                if (expressTaker.getState() == 1) {
                    User user = new User();
                    user.setId(token.getId());
                    user = userService.getById(user);
                    if (!(surcharge > expressTaker.getSurcharge() + user.getBalance())) {
                        expressTaker.setSurcharge(surcharge);
                        int count = expressTakerService.updateOne(expressTaker);
                        if (count > 0) {
                            map.put("status", 0);
                            map.put("tip", "success");
                        } else {
                            map.put("status", -5);
                            map.put("tip", "修改失敗,請稍後再試");
                        }
                    } else {
                        map.put("status", -4);
                        map.put("tip", "您當前餘額不足以支付該筆打賞");
                    }
                } else {
                    map.put("status", -3);
                    map.put("tip", "當前狀態不允許修改打賞金額");
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
    @RequestMapping("changeExpressTakerState")
    public Map<String, Object> changeExpressTakerState(int id, int state) {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            ExpressTaker expressTaker = new ExpressTaker();
            expressTaker.setUser_id(token.getId());
            expressTaker.setId(id);
            expressTaker = expressTakerService.getById(expressTaker);
            if (expressTaker != null) {
                if (state == 6) {//取消訂單
                    if (expressTaker.getState() == 1 || expressTaker.getState() == 2) {
                        expressTaker.setState(state);
                        int count = expressTakerService.updateOne(expressTaker);
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
                } else if (state == 4) {
                    if (expressTaker.getState() == 3) {
                        expressTaker.setState(state);
                        int count = expressTakerService.updateOne(expressTaker);
                        if (count > 0) {
                            map.put("status", 0);
                            map.put("tip", "success");
                        } else {
                            map.put("status", -5);
                            map.put("tip", "確認收貨成功,請稍後再試");
                        }
                    } else {
                        map.put("status", -3);
                        map.put("tip", "當前狀態不允許確認收貨");
                    }
                } else {
                    map.put("status", -6);
                    map.put("tip", "當前狀態不允許確認收貨");
                }
            } else {
                map.put("status", -7);
                map.put("tip", "非法請求");
            }
        } else {
            map.put("status", -1);
            map.put("tip", "登錄已失效，請重新登錄");
        }
        return map;
    }

    @LoginToken
    @RequestMapping("addExpressTaker")
    public Map<String, Object> addExpressTaker(String logistics_name, String logistics_num, String consignee_name, int consignee_phone, String pickup_points, String appointment_time, String post_name, String post_line, String post_phone, String remark) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            User user = new User();
            user.setId(token.getId());
            user = userService.getById(user);
            if (user != null) {
                float cost = consignee_phone;
                if (consignee_phone<=0) {
                    map.put("status", -4);
                    map.put("tip", "跟我玩這套?老弟");
                    return map;
                }
                if (user.getBalance() >= cost) {
                    ExpressTaker expressTaker = new ExpressTaker();
                    expressTaker.setUser_id(token.getId());
                    expressTaker.setState(1);
                    expressTaker.setExpress_name(logistics_name);
                    expressTaker.setExpress_num(logistics_num);
                    int count = expressTakerService.getCount(expressTaker);
                    if (!(count > 0)) {
                        expressTaker.setType(1);
                        expressTaker.setContact_name(post_name);
                        expressTaker.setLine(post_line);
                        expressTaker.setPhone(post_phone);
                        expressTaker.setAppointment_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(appointment_time));
                        expressTaker.setAppointment_points(pickup_points);
                        expressTaker.setExpress_type(0);
                        expressTaker.setAmount(cost);
                        expressTaker.setCommission((float) (cost * 0.1));
                        expressTaker.setActual_amount(expressTaker.getAmount() - expressTaker.getCommission());
                        expressTaker.setTake_points(0);
                        expressTaker.setRecipient_name(consignee_name);
                        expressTaker.setRecipient_phone("");
                        expressTaker.setRemark(remark);
                        count = expressTakerService.insertOne(expressTaker);
                        if (count > 0) {
                            map.put("status", 0);
                            map.put("tip", "success");
                        } else {
                            map.put("status", -6);
                            map.put("tip", "提交失敗,請稍後再試");
                        }
                    } else {
                        map.put("status", -2);
                        map.put("tip", "該訂單已存在,請勿重複提交");
                    }
                } else {
                    map.put("status", -5);
                    map.put("tip", "您當前餘額不足以支付" + cost + "元");
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
    @RequestMapping("getExpressTakerInfoByIssuance")
    public Map<String, Object> getExpressTakerInfoByIssuance(int id) {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            ExpressTaker expressTaker = new ExpressTaker();
            expressTaker.setId(id);
            expressTaker.setUser_id(token.getId());
            expressTaker.setDeleted(false);
            expressTaker = expressTakerService.getById(expressTaker);
            if (expressTaker != null) {
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
                ExpressReceive expressReceive = new ExpressReceive();
                expressReceive.setExpress_taker_id(expressTaker.getId());
                expressReceive = expressReceiveService.getByIssuance(expressReceive);
                if (expressReceive != null) {
                    map.put("theUserId", expressReceive.getUser_id());
                    map.put("theRealName", expressReceive.getReal_name());
                    map.put("thePhone", expressReceive.getPhone());
                    map.put("theLine", expressReceive.getLine());
                    ExpressIntegral expressIntegral = new ExpressIntegral();
                    expressIntegral.setUser_id(expressReceive.getUser_id());
                    expressIntegral = expressIntegralService.getByUserId(expressIntegral);
                    if (expressIntegral != null) {
                        map.put("theGrade", expressIntegral.getGrade());
                    } else {
                        map.put("theGrade", "-1");
                    }
                    map.put("theAllTotal", expressReceiveService.getCountByTotal(expressReceive));
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
