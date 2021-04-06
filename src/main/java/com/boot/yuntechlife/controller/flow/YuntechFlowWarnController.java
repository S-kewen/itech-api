package com.boot.yuntechlife.controller.flow;

import com.boot.yuntechlife.annotation.LoginToken;
import com.boot.yuntechlife.entity.flow.YuntechFlowSet;
import com.boot.yuntechlife.entity.flow.YuntechFlowWarn;
import com.boot.yuntechlife.entity.main.Token;
import com.boot.yuntechlife.entity.user.User;
import com.boot.yuntechlife.service.flow.YuntechFlowSetService;
import com.boot.yuntechlife.service.flow.YuntechFlowWarnService;
import com.boot.yuntechlife.service.token.TokenService;
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
 * @ClassName: YuntechFlowWarnController
 * @Description: Controller
 * @Date: 2020-03-10
 */
@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://courier.iskwen.com", maxAge = 3600)
public class YuntechFlowWarnController {
    @Autowired
    private YuntechFlowWarnService yuntechFlowWarnService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private Util util;
    @Autowired
    private HttpServletRequest request;

    @LoginToken
    @RequestMapping("listYuntechFlowWarn")
    public Map<String, Object> listYuntechFlowWarn(int pageNumber, int pageSize, String sortName, String sortOrder, String keyword, int state, String startTime, String endTime) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            User user = new User();
            user.setId(token.getId());
            user.setSortName(sortName);
            user.setSortOrder(sortOrder);
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
            List<YuntechFlowWarn> select = yuntechFlowWarnService.getList(user);
            PageInfo<YuntechFlowWarn> pageInfo = new PageInfo<>(select);
            map.put("status", 0);
            map.put("tip", "success");
            map.put("total", pageInfo.getTotal());
            map.put("list", pageInfo.getList());
//            return pageInfo;
        } else {
            map.put("status", -1);
            map.put("tip", "登錄已失效，請重新登錄");
        }
//        return json;
        return map;
    }

    @LoginToken
    @RequestMapping("deleteYuntechFlowWarn")
    public Map<String, Object> deleteYuntechFlowWarn(int id) {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            YuntechFlowWarn yuntechFlowWarn = new YuntechFlowWarn();
            yuntechFlowWarn.setUser_id(token.getId());
            yuntechFlowWarn.setId(id);
            int count = yuntechFlowWarnService.deleteOne(yuntechFlowWarn);
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

}
