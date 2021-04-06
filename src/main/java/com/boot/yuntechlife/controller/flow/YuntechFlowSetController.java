package com.boot.yuntechlife.controller.flow;

import com.boot.yuntechlife.annotation.LoginToken;
import com.boot.yuntechlife.annotation.PassToken;
import com.boot.yuntechlife.entity.flow.YuntechFlow;
import com.boot.yuntechlife.entity.flow.YuntechFlowConfig;
import com.boot.yuntechlife.entity.flow.YuntechFlowSet;
import com.boot.yuntechlife.entity.main.Token;
import com.boot.yuntechlife.entity.user.BindingLine;
import com.boot.yuntechlife.entity.user.User;
import com.boot.yuntechlife.service.flow.YuntechFlowConfigService;
import com.boot.yuntechlife.service.flow.YuntechFlowService;
import com.boot.yuntechlife.service.flow.YuntechFlowSetService;
import com.boot.yuntechlife.service.token.TokenService;
import com.boot.yuntechlife.service.user.BindingLineService;
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
 * @ClassName: YuntechFlowSetController
 * @Description: Controller
 * @Date: 2020-03-10
 */
@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://courier.iskwen.com", maxAge = 3600)
public class YuntechFlowSetController {
    @Autowired
    private YuntechFlowSetService yuntechFlowSetService;
    @Autowired
    private YuntechFlowConfigService yuntechFlowConfigService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private Util util;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private BindingLineService bindingLineService;
    @LoginToken
    @RequestMapping("listYuntechFlowSet")
    public Map<String, Object> listYuntechFlowSet(int pageNumber, int pageSize, String sortName, String sortOrder, String keyword, int state, String startTime, String endTime) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            User user = new User();
            user.setId(token.getId());
            user.setState(state);
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
                order = sortName;
                if (sortOrder != "" && sortOrder != "") {
                    order += " " + sortOrder;
                }
            }
            PageHelper.startPage(pageNumber, pageSize, order);
            List<YuntechFlowSet> select = yuntechFlowSetService.getList(user);
            PageInfo<YuntechFlowSet> pageInfo = new PageInfo<>(select);
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
    @RequestMapping("deleteYuntechFlowSet")
    public Map<String, Object> deleteYuntechFlowSet(int id) {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            YuntechFlowSet yuntechFlowSet = new YuntechFlowSet();
            yuntechFlowSet.setUser_id(token.getId());
            yuntechFlowSet.setId(id);
            int count = yuntechFlowSetService.deleteOne(yuntechFlowSet);
            if (count > 0) {
                map.put("status", 0);
                map.put("tip", "success");
            } else {
                map.put("status", -2);
                map.put("tip", "刪除失敗,請稍候再試");
            }

        } else {
            map.put("status", -1);
            map.put("tip", "登錄已失效，請重新登錄");
        }

        return map;
    }

    @LoginToken
    @RequestMapping("addYuntechFlowSet")
    public Map<String, Object> addYuntechFlowSet(String ip, float warnValue, String warnMail, int mailState, int mailInterval, String warnPhone, int phoneState, int phoneInterval, String remark) {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            YuntechFlowSet yuntechFlowSet = new YuntechFlowSet();
            yuntechFlowSet.setUser_id(token.getId());
            yuntechFlowSet.setIp(ip);
            int count = yuntechFlowSetService.getCount(yuntechFlowSet);
            if (count == 0) {
                yuntechFlowSet.setState(1);
                yuntechFlowSet.setWarn_value(warnValue);
                yuntechFlowSet.setWarn_mail(warnMail);
                yuntechFlowSet.setMail_state(mailState);
                yuntechFlowSet.setMail_interval(mailInterval);
                yuntechFlowSet.setWarn_phone(warnPhone);
                yuntechFlowSet.setPhone_state(phoneState);
                yuntechFlowSet.setPhone_interval(phoneInterval);
                yuntechFlowSet.setRemark(remark);
                count = yuntechFlowSetService.insertOne(yuntechFlowSet);
                if (count > 0) {
                    map.put("status", 0);
                    map.put("tip", "success");
                } else {
                    map.put("status", -2);
                    map.put("tip", "添加失敗,請稍候再試");
                }
            } else {
                map.put("status", -2);
                map.put("tip", "該配置已存在,請勿重複添加");
//                map.put("tip", "目前暫不允許同時監測多個ip,謝謝配合");
            }
        } else {
            map.put("status", -1);
            map.put("tip", "登錄已失效，請重新登錄");
        }

        return map;
    }

    @LoginToken
    @RequestMapping("changeYuntechFlowSetState")
    public Map<String, Object> addYuntechFlowSet(int id, int state) {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            YuntechFlowSet yuntechFlowSet = new YuntechFlowSet();
            yuntechFlowSet.setUser_id(token.getId());
            yuntechFlowSet.setState(state);
            if (state == 1) {
                YuntechFlowConfig yuntechFlowConfig = new YuntechFlowConfig();
                yuntechFlowConfig.setUser_id(token.getId());
                yuntechFlowConfig.setState(1);
                yuntechFlowConfig = yuntechFlowConfigService.getById(yuntechFlowConfig);
                if (yuntechFlowConfig != null) {
                    int count = yuntechFlowSetService.getCount(yuntechFlowSet);
                    if (count >= yuntechFlowConfig.getMax_enable()) {
                        map.put("status", -3);
                        map.put("tip", "您當前只能監測" + yuntechFlowConfig.getMax_enable() + "個ip");
                        return map;
                    }
                } else {
                    map.put("status", -4);
                    map.put("tip", "您當前不允許進行監測");
                    return map;
                }
            }
            yuntechFlowSet.setId(id);
            int count = yuntechFlowSetService.updateOne(yuntechFlowSet);
            if (count > 0) {
                map.put("status", 0);
                map.put("tip", "success");
            } else {
                map.put("status", -2);
                map.put("tip", "更新失敗,請稍候再試");
            }
        } else {
            map.put("status", -1);
            map.put("tip", "登錄已失效，請重新登錄");
        }

        return map;
    }

    @LoginToken
    @RequestMapping("updateYuntechFlowSet")
    public Map<String, Object> updateYuntechFlowSet(int id, String ip, float warnValue, String warnMail, int mailState, int mailInterval, String warnPhone, int phoneState, int phoneInterval, String remark) {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            YuntechFlowSet yuntechFlowSet = new YuntechFlowSet();
            yuntechFlowSet.setUser_id(token.getId());
            yuntechFlowSet.setId(id);
            yuntechFlowSet.setIp(ip);
            int count = yuntechFlowSetService.getCount(yuntechFlowSet);
            if (count > 0) {
                map.put("status", -2);
                map.put("tip", "該配置已存在,請勿重複添加");
                return map;
            }
            yuntechFlowSet.setWarn_value(warnValue);
            yuntechFlowSet.setWarn_mail(warnMail);
            yuntechFlowSet.setMail_state(mailState);
            yuntechFlowSet.setMail_interval(mailInterval);
            yuntechFlowSet.setWarn_phone(warnPhone);
            yuntechFlowSet.setPhone_state(phoneState);
            yuntechFlowSet.setPhone_interval(phoneInterval);
            yuntechFlowSet.setRemark(remark);
            count = yuntechFlowSetService.updateOne(yuntechFlowSet);
            if (count > 0) {
                map.put("status", 0);
                map.put("tip", "success");
            } else {
                map.put("status", -2);
                map.put("tip", "更新失敗,請稍候再試");
            }
        } else {
            map.put("status", -1);
            map.put("tip", "登錄已失效，請重新登錄");
        }

        return map;
    }

    @LoginToken
    @RequestMapping("getYuntechFlowSetInfo")
    public Map<String, Object> getYuntechFlowSetInfo(int id) {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            YuntechFlowSet yuntechFlowSet = new YuntechFlowSet();
            yuntechFlowSet.setUser_id(token.getId());
            yuntechFlowSet.setId(id);
            yuntechFlowSet = yuntechFlowSetService.getById(yuntechFlowSet);
            if (yuntechFlowSet != null) {
                map.put("status", 0);
                map.put("tip", "success");
                map.put("ip", yuntechFlowSet.getIp());
                map.put("warnValue", yuntechFlowSet.getWarn_value());
                map.put("warnMail", yuntechFlowSet.getWarn_mail());
                map.put("warnPhone", yuntechFlowSet.getWarn_phone());
                map.put("mailState", yuntechFlowSet.getMail_state());
                map.put("phoneState", yuntechFlowSet.getPhone_state());
                map.put("mailInterval", yuntechFlowSet.getMail_interval());
                map.put("phoneInterval", yuntechFlowSet.getPhone_interval());
                map.put("remark", yuntechFlowSet.getRemark());
            } else {
                map.put("status", -2);
                map.put("tip", "該配置不存在");
            }
        } else {
            map.put("status", -1);
            map.put("tip", "登錄已失效，請重新登錄");
        }
        return map;
    }

    @PassToken
    @RequestMapping("listYuntechFlowSetByLineUserId")
    public Map<String, Object> listYuntechFlowSetByLineId(String lineUserId) {
        Map<String, Object> map = new HashMap<>();
        if(lineUserId==null || lineUserId==""){
            map.put("status", -2);
            map.put("tip", "该用户不存在");
            return map;
        }
        BindingLine bindingLine = new BindingLine();
        bindingLine.setLine_user_id(lineUserId);
        bindingLine=bindingLineService.getInfoByLine(bindingLine);
        if(bindingLine!=null){
            User user = new User();
            user.setId(bindingLine.getUser_id());
            PageHelper.startPage(1, 100);
            List<YuntechFlowSet> select = yuntechFlowSetService.getList(user);
            PageInfo<YuntechFlowSet> pageInfo = new PageInfo<>(select);
            map.put("status", 0);
            map.put("tip", "success");
            map.put("total", pageInfo.getTotal());
            map.put("list", pageInfo.getList());
        }else{
            map.put("status", -2);
            map.put("tip", "该用户不存在");
        }
        return map;
    }
}
