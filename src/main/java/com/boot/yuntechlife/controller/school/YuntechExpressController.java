package com.boot.yuntechlife.controller.school;

import com.boot.yuntechlife.annotation.LoginToken;
import com.boot.yuntechlife.entity.school.YuntechExpress;
import com.boot.yuntechlife.entity.main.Token;
import com.boot.yuntechlife.entity.user.LoginRecord;
import com.boot.yuntechlife.entity.user.User;
import com.boot.yuntechlife.service.school.YuntechExpressService;
import com.boot.yuntechlife.service.token.TokenService;
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
 * @ClassName: YuntechExpressController
 * @Description: controller
 * @Date: 2020-03-19
 */
@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://courier.iskwen.com", maxAge = 3600)
public class YuntechExpressController {
    @Autowired
    private UserService userService;
    @Autowired
    private YuntechExpressService yuntechExpressService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private Util util;
    @Autowired
    private HttpServletRequest request;

    @LoginToken
    @RequestMapping("listYuntechExpress")
    public Map<String, Object> listYuntechExpress(int pageNumber, int pageSize, String sortName, String sortOrder, String keyword, int region, int state, String startTime, String endTime) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            User user = new User();
            user.setId(token.getId());
            user.setSortName(sortName);
            user.setSortOrder(sortOrder);
            user.setKeyword(keyword);
            user.setRegion(region);
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
            List<YuntechExpress> select = yuntechExpressService.getList(user);
            PageInfo<YuntechExpress> pageInfo = new PageInfo<>(select);
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
