package com.boot.yuntechlife.controller.system;

import com.boot.yuntechlife.annotation.LoginToken;
import com.boot.yuntechlife.entity.main.Token;
import com.boot.yuntechlife.entity.system.Advert;
import com.boot.yuntechlife.entity.system.Notice;
import com.boot.yuntechlife.service.system.AdvertService;
import com.boot.yuntechlife.service.system.NoticeService;
import com.boot.yuntechlife.service.token.TokenService;
import com.boot.yuntechlife.service.user.UserService;
import com.boot.yuntechlife.util.Util;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: skwen
 * @ClassName: NoticeController
 * @Description: controller
 * @Date: 2020-03-17
 */
@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://courier.iskwen.com", maxAge = 3600)
public class AdvertController {
    @Autowired
    private UserService userService;
    @Autowired
    private AdvertService advertService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private Util util;
    @Autowired
    private HttpServletRequest request;

    @LoginToken
    @RequestMapping("listAdvert")
    public Map<String, Object> listAdvert() {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            Advert advert = new Advert();
            PageHelper.startPage(1, 100);
            List<Advert> select = advertService.getList(advert);
            PageInfo<Advert> pageInfo = new PageInfo<>(select);
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
