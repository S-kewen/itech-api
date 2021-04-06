package com.boot.yuntechlife.config;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.boot.yuntechlife.annotation.LoginToken;
import com.boot.yuntechlife.annotation.PassToken;
import com.boot.yuntechlife.entity.main.Token;
import com.boot.yuntechlife.entity.user.User;
import com.boot.yuntechlife.service.token.TokenService;
import com.boot.yuntechlife.service.user.UserService;
import com.boot.yuntechlife.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: skwen
 * ClassName: AuthenticationInterceptor
 * @Description: token校驗過濾器
 * @Date: 2020-03-08
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private Util util;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private HttpServletRequest request;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
//        Map<String, Object> map = new HashMap<>();
//        map.put("handle",util.getRandomStr(2,32,""));
        String tokenStr = httpServletRequest.getHeader("Authorization");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            //map.put("info","skip");
            //addJson(httpServletResponse,map);
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                //map.put("info","pass");
                //addJson(httpServletResponse,map);
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(LoginToken.class)) {
            LoginToken userLoginToken = method.getAnnotation(LoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (tokenStr == null || tokenStr == "") {
                    //System.out.println("無token,請重新登錄");
//                    throw new RuntimeException("無token,請重新登錄");
                    //map.put("info","deny");
                    //map.put("status",-1000);
                    //map.put("tip","當前token不存在,請重新登錄");
                    returnJson(httpServletResponse, "{\"tip\":\"當前token不存在,請重新登錄\",\"status\":-1000}");
                    return false;
                }
                // 获取 token 中的 username
                Token token = new Token();
                try {
                    token = tokenService.parseToken(tokenStr);
                } catch (JWTDecodeException j) {
                    //System.out.println("401解析错误");
                    //throw new RuntimeException("401,請重新登錄");
//                    map.put("info","deny");
//                    map.put("status",-1001);
//                    map.put("tip","當前token解析錯誤,請重新登錄");
                    //addJson(httpServletResponse,map);
                    returnJson(httpServletResponse, "{\"tip\":\"當前token解析錯誤,請重新登錄\",\"status\":-1001}");
                    return false;
                }
                if (token == null) {
                    //System.out.println("401解析错误");
                    //throw new RuntimeException("401,請重新登錄");
//                    map.put("info","deny");
//                    map.put("status",-1001);
//                    map.put("tip","當前token解析錯誤,請重新登錄");
                    //addJson(httpServletResponse,map);
                    returnJson(httpServletResponse, "{\"tip\":\"當前token已消失,請重新登錄\",\"status\":-1006}");
                    return false;
                }
                if (new Date().after(token.getExpire_time())) {
                    //System.out.println("token已过期");
                    //throw new RuntimeException("token已過期,請重新登錄");
//                    map.put("info","deny");
//                    map.put("status",-1002);
//                    map.put("tip","當前token已過期,請重新登錄");
                    //addJson(httpServletResponse,map);
                    returnJson(httpServletResponse, "{\"tip\":\"當前token已過期,請重新登錄\",\"status\":-1002}");
                    return false;
                }
                User user = new User();
                user.setUsername(token.getUsername());
                user = userService.getByUsername(user);
                if (user == null) {
                    // System.out.println("token已失效");
                    //throw new RuntimeException("token已失效,請重新登錄");//用户不存在，请重新登录
//                    map.put("info","deny");
//                    map.put("status",-1003);
//                    map.put("tip","當前token已失效,請重新登錄");
//                    //addJson(httpServletResponse,map);
                    returnJson(httpServletResponse, "{\"tip\":\"當前token已失效,請重新登錄\",\"status\":-1003}");
                    return false;
                } else {
                    if (user.getState() != 1) {
                        //throw new RuntimeException("用戶狀態異常,請重新登錄");//用户狀態異常
//                        map.put("info","deny");
//                        map.put("status",-1004);
//                        map.put("tip","用戶狀態異常,請重新登錄");
                        //addJson(httpServletResponse,map);
                        returnJson(httpServletResponse, "{\"tip\":\"用戶狀態異常,請重新登錄\",\"status\":-1004}");
                        return false;
                    }
                    if (!token.getPassword().equals(util.getMd5(user.getPassword()))) {
                        //throw new RuntimeException("用戶密碼錯誤,請重新登錄");//用户狀態異常
//                        map.put("info","deny");
//                        map.put("status",-1005);
//                        map.put("tip","用戶密碼錯誤,請重新登錄");
                        //addJson(httpServletResponse,map);
                        returnJson(httpServletResponse, "{\"tip\":\"用戶密碼錯誤,請重新登錄\",\"status\":-1005}");
                        return false;
                    }
//                    if("1".equals(redisService.get("checkTokenByIp"))){
//                     if(!token.getIp().equals(otherService.getMd5(otherService.getLocalIp(request)))){
//                        //throw new RuntimeException("用戶IP異常,請重新登錄");//用户狀態異常
//                    map.put("info","deny");
//                    map.put("status",-1006);
//                    map.put("tip","用戶IP異常,請重新登錄");
//                    addJson(httpServletResponse,map);
//                    return false;
//                    }
//                    }
                }
                // 验证 token
                return true;
            }
        }
        return true;
    }

    private void returnJson(HttpServletResponse response, String json) throws Exception {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);
        } catch (IOException e) {
            System.out.println(e.toString());
        } finally {
            if (writer != null)
                writer.close();
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}