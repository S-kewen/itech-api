package com.boot.yuntechlife.controller.flow;

import com.boot.yuntechlife.annotation.LoginToken;
import com.boot.yuntechlife.annotation.PassToken;
import com.boot.yuntechlife.entity.flow.YuntechFlow;
import com.boot.yuntechlife.entity.flow.YuntechFlowSet;
import com.boot.yuntechlife.entity.main.Token;
import com.boot.yuntechlife.entity.user.Transaction;
import com.boot.yuntechlife.entity.user.User;
import com.boot.yuntechlife.service.flow.YuntechFlowService;
import com.boot.yuntechlife.service.flow.YuntechFlowSetService;
import com.boot.yuntechlife.service.token.TokenService;
import com.boot.yuntechlife.service.user.TransactionService;
import com.boot.yuntechlife.util.Util;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: skwen
 * @ClassName: YuntechFlowController
 * @Description: Controller
 * @Date: 2020-03-10
 */
@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://courier.iskwen.com", maxAge = 3600)
public class YuntechFlowController {
    @Autowired
    private YuntechFlowSetService yuntechFlowSetService;
    @Autowired
    private YuntechFlowService yuntechFlowService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private Util util;
    @Autowired
    private HttpServletRequest request;

    @LoginToken
    @RequestMapping("listYuntechFlow")
    public Map<String, Object> listYuntechFlow(int pageNumber, int pageSize, String sortName, String sortOrder, String keyword, int type, String startTime, String endTime) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            User user = new User();
            user.setId(token.getId());
            user.setSortName(sortName);
            user.setSortOrder(sortOrder);
            user.setKeyword(keyword);
            if (startTime != null && startTime != "") {
                user.setStartTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime));
            }
            if (endTime != null && endTime != "") {
                user.setEndTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime));
            }
            String order = "";
            if (sortName != "" && sortName != "") {
                order = "yuntech_flow." + sortName;
                if (sortOrder != "" && sortOrder != "") {
                    order += " " + sortOrder;
                }
            }
            PageHelper.startPage(pageNumber, pageSize, order);
            List<YuntechFlow> select;
            if (type == 0) {
                select = yuntechFlowService.getList(user);
            } else {
                select = yuntechFlowService.getListByDay(user);
            }
            PageInfo<YuntechFlow> pageInfo = new PageInfo<>(select);
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
    @RequestMapping("getEchartsInfoByWeek")
    public Map<String, Object> getEchartsInfoByWeek(String ip, String nowDate) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            if (ip == null || ip == "" || nowDate == null || nowDate == "") {
                map.put("status", -2);
                map.put("tip", "參數缺失,非法請求");
                return map;
            }
            YuntechFlowSet yuntechFlowSet = new YuntechFlowSet();
            yuntechFlowSet.setIp(ip);
            yuntechFlowSet.setUser_id(token.getId());
            int count = yuntechFlowSetService.getCount(yuntechFlowSet);
            if (count > 0) {
                YuntechFlow yuntechFlow = new YuntechFlow();
                yuntechFlow.setIp(ip);
                yuntechFlow.setAdd_time(new SimpleDateFormat("yyyy-MM-dd").parse(nowDate));
                PageHelper.startPage(1, 7);
                List<YuntechFlow> select = yuntechFlowService.getDayInfoByWeek(yuntechFlow);
                PageInfo<YuntechFlow> pageInfo = new PageInfo<>(select);
                map.put("status", 0);
                map.put("tip", "success");
                map.put("total", pageInfo.getTotal());
                map.put("list", pageInfo.getList());
            } else {
                map.put("status", -3);
                map.put("tip", "權限不足");
            }
        } else {
            map.put("status", -1);
            map.put("tip", "登錄已失效，請重新登錄");
        }
        return map;
    }

    @LoginToken
    @RequestMapping("getEchartsInfoByHistory")
    public Map<String, Object> getEchartsInfoByHistory(String ip) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            if (ip == null || ip == "") {
                map.put("status", -2);
                map.put("tip", "參數缺失,非法請求");
                return map;
            }
            YuntechFlowSet yuntechFlowSet = new YuntechFlowSet();
            yuntechFlowSet.setIp(ip);
            yuntechFlowSet.setUser_id(token.getId());
            int count = yuntechFlowSetService.getCount(yuntechFlowSet);
            if (count > 0) {
                User user = new User();
                user.setId(token.getId());
                user.setKeyword(ip);
                PageHelper.startPage(1, 5000);
                List<YuntechFlow> select = yuntechFlowService.getList(user);
                PageInfo<YuntechFlow> pageInfo = new PageInfo<>(select);
                map.put("status", 0);
                map.put("tip", "success");
                map.put("total", pageInfo.getTotal());
                map.put("showSize", pageInfo.getList().size());
                map.put("list", pageInfo.getList());
            } else {
                map.put("status", -3);
                map.put("tip", "權限不足");
            }
        } else {
            map.put("status", -1);
            map.put("tip", "登錄已失效，請重新登錄");
        }
        return map;
    }

    @LoginToken
    @RequestMapping("getEchartsInfoByAll")
    public Map<String, Object> getEchartsInfoByAll(String ip) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            if (ip == null || ip == "") {
                map.put("status", -2);
                map.put("tip", "參數缺失,非法請求");
                return map;
            }
            YuntechFlowSet yuntechFlowSet = new YuntechFlowSet();
            yuntechFlowSet.setIp(ip);
            yuntechFlowSet.setUser_id(token.getId());
            int count = yuntechFlowSetService.getCount(yuntechFlowSet);
            if (count > 0) {
                YuntechFlow yuntechFlow = new YuntechFlow();
                yuntechFlow.setIp(ip);
                YuntechFlow select = yuntechFlowService.getSumByIp(yuntechFlow);
                map.put("status", 0);
                map.put("tip", "success");
                map.put("allInsUp", select.getIns_up());
                map.put("allInsDowns", select.getIns_down());
                map.put("allExtUps", select.getExt_up());
                map.put("allExtDowns", select.getExt_down());
                map.put("allFlow", select.getFlow());
                map.put("dayNum", yuntechFlowService.getDayNumByIp(yuntechFlow));
            } else {
                map.put("status", -3);
                map.put("tip", "權限不足");
            }
        } else {
            map.put("status", -1);
            map.put("tip", "登錄已失效，請重新登錄");
        }
        return map;
    }

    @PassToken
    @RequestMapping("getByIp")
    public Map<String, Object> getByIp(String ip) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        if (ip == null || ip == "") {
            map.put("status", -2);
            map.put("tip", "參數缺失,非法請求");
            return map;
        }
        YuntechFlow yuntechFlow = new YuntechFlow();
        yuntechFlow.setIp(ip);
        yuntechFlow = yuntechFlowService.getByIp(yuntechFlow);
        if (yuntechFlow != null) {
//                map.put("status", 0);
//                map.put("tip", "success");
            map.put("ip", yuntechFlow.getIp());
            map.put("flow", new BigDecimal(yuntechFlow.getFlow()).setScale(2, RoundingMode.HALF_UP).toString());
            map.put("add_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(yuntechFlow.getAdd_time()));
        } else {
            map.put("status", -3);
            map.put("tip", "沒有可查的記錄");
        }
        return map;
    }
//    @LoginToken
//    @RequestMapping("deleteYuntechFlow")
//    public Map<String, Object> deleteYuntechFlow(int id) {
//        Map<String,Object> map = new HashMap<>();
//        Token token = new Token();
//        token=tokenService.parseToken(request.getHeader("Authorization"));
//        if (token!=null) {
//            YuntechFlow yuntechFlow = new YuntechFlow();
//            yuntechFlow.setId(id);
//            int count=yuntechFlowService.deleteOne(yuntechFlow);
//            if (count>0){
//                map.put("status",0);
//                map.put("tip","success");
//            }else{
//                map.put("status",-2);
//                map.put("tip","刪除失敗,請稍候再試");
//            }
//
//        }else{
//            map.put("status", -1);
//            map.put("tip", "登錄已失效，請重新登錄");
//        }
//
//        return map;
//    }

    @PassToken
    @RequestMapping("addYuntechFlow")
    public Map<String, Object> addYuntechFlow(String ip, Double insUp, Double insDown, Double extUp, Double extDown, Double flow, Double ratio) {
        Map<String, Object> map = new HashMap<>();
        if (ip == null || ip == "") {
            map.put("status", -2);
            map.put("tip", "參數缺失,非法請求");
            return map;
        }
        DecimalFormat df = new DecimalFormat("0");

        YuntechFlow yuntechFlow = new YuntechFlow();
        yuntechFlow.setIp(ip);
        yuntechFlow.setIns_up(insUp);
        yuntechFlow.setIns_down(insDown);
        yuntechFlow.setExt_up(extUp);
        yuntechFlow.setExt_down(extDown);
        yuntechFlow.setFlow(flow);
        yuntechFlow.setRatio(ratio);
        int count = yuntechFlowService.insertOne(yuntechFlow);
        if (count > 0) {
            map.put("status", 0);
            map.put("tip", "success");
        } else {
            map.put("status", -3);
            map.put("tip", "添加失败,请稍后再试");
        }
        return map;
    }
}
