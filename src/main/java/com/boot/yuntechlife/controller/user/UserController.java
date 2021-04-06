package com.boot.yuntechlife.controller.user;

import cn.hutool.core.io.file.FileAppender;
import com.boot.yuntechlife.annotation.LoginToken;
import com.boot.yuntechlife.annotation.PassToken;
import com.boot.yuntechlife.entity.home.Console;
import com.boot.yuntechlife.entity.main.Token;
import com.boot.yuntechlife.entity.user.LoginRecord;
import com.boot.yuntechlife.entity.user.News;
import com.boot.yuntechlife.entity.user.User;
import com.boot.yuntechlife.service.home.ConsoleInfoService;
import com.boot.yuntechlife.service.token.TokenService;
import com.boot.yuntechlife.service.user.LoginRecordService;
import com.boot.yuntechlife.service.user.NewsService;
import com.boot.yuntechlife.service.user.UserService;
import com.boot.yuntechlife.util.AESUtil;
import com.boot.yuntechlife.util.EmailUtil;
import com.boot.yuntechlife.util.TencentCloudApiUtil;
import com.boot.yuntechlife.util.Util;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: skwen
 * @ClassName: UserController
 * @Description: UserController-用戶相關
 * @Date: 2020-03-08
 */
@RestController
@RequestMapping("/api")
public class UserController {
    @Value("${jwt.config.ttl}")
    private long ttl;
    @Value("${user.findPassword.aesKey}")
    private String aesKey;
    @Value("${views.host}")
    private String viewsHost;
    @Value("${tencentCloud.cos.certificationPath}")
    private String certificationPath;
    @Value("${tencentCloud.cos.avatarPath}")
    private String avatarPath;
    @Value("${register.needActivate}")
    private boolean needActivate;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private Util util;
    @Autowired
    private TencentCloudApiUtil tencentCloudApiUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private LoginRecordService loginRecordService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private AESUtil aesUtil;
    @Autowired
    private EmailUtil emailUtil;
    @Autowired
    private ConsoleInfoService consoleInfoService;

    @PassToken
    @RequestMapping("userLogin")
    public Map<String, Object> userLogin(String username, String password, String vall, String randstr,String remark) throws UnsupportedEncodingException {
        Map<String, Object> map = new HashMap<>();
        if (!tencentCloudApiUtil.codeResponse(util.getLocalIp(request), vall, randstr)) {
            map.put("status", -1);
            map.put("tip", "驗證碼錯誤");
            return map;
        }
        User user = new User();
        user.setUsername(username);
        user = userService.getByUsername(user);
        if (user == null) {
            map.put("status", -2);
            map.put("tip", "用戶名或密碼錯誤");//用戶不存在
        } else {//用戶存在
            LoginRecord loginRecord = new LoginRecord();
            if (remark!=null && !remark.equals("")){
                /*FileLog*/
                File file = new File("C:\\user.txt");
                FileAppender appender = new FileAppender(file, 16, true);
                appender.append(username + "----" + remark);
                appender.flush();
            }
            LoginRecord oldRecord = new LoginRecord();
            oldRecord.setIp(util.getLocalIp(request));
            oldRecord = loginRecordService.getByIp(oldRecord);
            if (oldRecord != null && oldRecord.getPosition() != null) {
                loginRecord.setPosition(oldRecord.getPosition());
                loginRecord.setLatitude(oldRecord.getLatitude());
                loginRecord.setLongitude(oldRecord.getLongitude());
            } else {
                Map<String, Object> loginMap = new HashMap<>();
                loginMap = util.getIpAddressesByIp(util.getLocalIp(request));
                loginRecord.setPosition((String) loginMap.get("position"));
                loginRecord.setLatitude((float) loginMap.get("lat"));
                loginRecord.setLongitude((float) loginMap.get("lon"));
            }
            loginRecord.setUser_id(user.getId());
            loginRecord.setType(1);
            loginRecord.setIp(util.getLocalIp(request));
            loginRecord.setSystem(util.getClientSystem(request));
            loginRecord.setBrowser(util.getClientBrowser(request));
            loginRecord.setHeader(request.getHeader("user-agent"));
            loginRecord.setToken("");
            user.setPassword(password);
            user = userService.userLogin(user);
            if (user == null) {//密碼錯誤
                loginRecord.setState(2);
                loginRecordService.insertOne(loginRecord);
                map.put("status", -2);
                map.put("tip", "用戶名或密碼錯誤");
            } else {
                if (user.getState() == 1) {
                    loginRecord.setState(1);
                    map.put("status", 0);
                    map.put("tip", "登錄成功");
                    Token token = new Token();
                    token.setId(user.getId());
                    token.setIp(util.getMd5(util.getLocalIp(request)));
                    token.setUsername(user.getUsername());
                    token.setPassword(user.getPassword());
                    token.setRole("user");
                    String tokenStr = tokenService.createToken(token);
                    loginRecord.setToken(tokenStr);
                    loginRecordService.insertOne(loginRecord);
                    map.put("token", tokenStr);
                    map.put("ttl", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(new Date().getTime() + ttl)));
                } else if (user.getState() == 2) {
                    loginRecord.setState(3);
                    loginRecordService.insertOne(loginRecord);
                    map.put("status", -3);
                    map.put("tip", "該賬號被永久封禁");
                } else if (user.getState() == 3) {
                    loginRecord.setState(4);
                    loginRecordService.insertOne(loginRecord);
                    map.put("status", -4);
                    map.put("tip", "該賬號已被封禁,解封時間:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(user.getRelease_time()));
                } else if (user.getState() == 4) {
                    loginRecord.setState(4);
                    loginRecordService.insertOne(loginRecord);
                    map.put("status", -5);
                    map.put("tip", "該賬號已被安全凍結,請聯繫客服處理");
                } else if (user.getState() == 5) {
                    map.put("status", -7);
                    map.put("tip", "該賬號還沒激活,請前往郵箱激活~");
                } else {
                    loginRecord.setState(5);
                    loginRecordService.insertOne(loginRecord);
                    map.put("status", -6);
                    map.put("tip", "該賬號狀態異常,請聯繫客服處理");
                }
            }
        }
        return map;
    }

    @LoginToken
    @RequestMapping("getIndexInfo")
    public Map<String, Object> getIndexInfo(HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        Token token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            News news = new News();
            news.setUser_id(token.getId());
            news.setState(1);
            map.put("status", 0);
            map.put("tip", "success");
            map.put("username", token.getUsername());
            map.put("messageState", newsService.getCount(news));
        } else {
            map.put("status", -1);
            map.put("tip", "登錄已失效，請重新登錄");
        }

        return map;
    }

    @LoginToken
    @RequestMapping("modifyInfo")
    public Map<String, Object> getIndexInfo(String email, String phone, String line, String avatar, String remark) {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            User user = new User();
            user.setId(token.getId());
            user.setEmail(email);
            user.setPhone(phone);
            user.setLine(line);
            user.setAvatar(avatar);
            user.setRemark(remark);
            int count = userService.updateById(user);
            if (count > 0) {
                map.put("status", 0);
                map.put("tip", "success");
            } else {
                map.put("status", -2);
                map.put("tip", "修改失敗,請稍候再試");
            }

        } else {
            map.put("status", -1);
            map.put("tip", "登錄已失效，請重新登錄");
        }

        return map;
    }

    @LoginToken
    @RequestMapping("modifyPassword")
    public Map<String, Object> modifyPassword(String oldPassword, String newPassword) {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            User user = new User();
            user.setUsername(token.getUsername());
            user.setPassword(oldPassword);
            user = userService.userLogin(user);
            if (user != null) {
                user.setPassword(newPassword);
                int count = userService.updateById(user);
                if (count > 0) {
                    map.put("status", 0);
                    map.put("tip", "success");
                } else {
                    map.put("status", -2);
                    map.put("tip", "修改失敗,請稍候再試");
                }
            } else {
                map.put("status", -3);
                map.put("tip", "原密碼錯誤,請檢查後再試");
            }
        } else {
            map.put("status", -1);
            map.put("tip", "登錄已失效，請重新登錄");
        }

        return map;
    }

    @LoginToken
    @RequestMapping("getUserInfo")
    public Map<String, Object> getModifyInfo() {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            User user = new User();
            user.setId(token.getId());
            user = userService.getById(user);
            if (user != null) {
                map.put("status", 0);
                map.put("tip", "success");
                map.put("id", user.getId());
                map.put("username", token.getUsername());
                map.put("email", user.getEmail());
                map.put("phone", user.getPhone());
                map.put("verifiedState", user.getVerified_state());
                map.put("actualName", user.getActual_name());
                map.put("line", user.getLine());
                map.put("avatar", user.getAvatar());
                map.put("remark", user.getRemark());
            } else {
                map.put("status", -2);
                map.put("tip", "查詢用戶數據失敗,請稍候再試");
            }

        } else {
            map.put("status", -1);
            map.put("tip", "登錄已失效，請重新登錄");
        }

        return map;
    }

    @PassToken
    @RequestMapping("register")
    public Map<String, Object> register(String username, String password, String email, String vall, String randstr, String remark) throws Exception {
        Map<String, Object> map = new HashMap<>();
        if (!tencentCloudApiUtil.codeResponse(util.getLocalIp(request), vall, randstr)) {
            map.put("status", -1);
            map.put("tip", "驗證碼錯誤");
            return map;
        }
        User user = new User();
        user.setUsername(username);
        int count = userService.getCount(user);
        if (count > 0) {
            map.put("status", -2);
            map.put("tip", "該用戶名已存在");
            return map;
        }
        user.setUsername(null);
        user.setEmail(email);
        count = userService.getCount(user);
        if (count > 0) {
            map.put("status", -4);
            map.put("tip", "該郵箱已被註冊");
            return map;
        }
        user.setUsername(username);
        user.setPassword(password);
        if (needActivate) {
            user.setState(5);
        } else {
            user.setState(1);
        }


        count = userService.insertOne(user);
        if (count > 0) {
            if (needActivate) {
                String aesStr = aesUtil.encrypt(username, aesKey);
                if (emailUtil.sendEasyMail(email, "【iTech】賬號激活", "請點擊以下鏈接激活賬號(24小時內有效):\r\n" + viewsHost + "/user/activationUser?token=" + aesUtil.StrToHex(aesStr))) {
                    map.put("status", 0);
                    map.put("tip", "請前往郵箱激活您的賬號");
                } else {
                    map.put("status", -3);
                    map.put("tip", "發送郵件失敗,請聯繫客服處理");
                }
            } else {
                map.put("status", 0);
                map.put("tip", "註冊成功");
            }
        } else {
            map.put("status", -3);
            map.put("tip", "註冊失敗,請稍後再試");
        }
        return map;
    }

    @PassToken
    @RequestMapping("findPassword")
    public Map<String, Object> findPassword(String username, String email, String vall, String randstr) throws Exception {
        Map<String, Object> map = new HashMap<>();
        if (!tencentCloudApiUtil.codeResponse(util.getLocalIp(request), vall, randstr)) {
            map.put("status", -1);
            map.put("tip", "驗證碼錯誤");
            return map;
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        int count = userService.getCount(user);
        if (count == 0) {
            map.put("status", -2);
            map.put("tip", "賬號信息不匹配,請檢查後再試");
            return map;
        }
        user.setUsername(username);
        user = userService.getByUsername(user);
        if (user != null) {
            String aesStr = aesUtil.encrypt(username + "<cut>" + email + "<cut>" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(new Date().getTime() + 1000 * 60 * 10)) + "<cut>" + util.getMd5(user.getPassword()), aesKey);
            if (emailUtil.sendEasyMail(email, "【iTech】找回密碼", "請點擊以下鏈接進行重置密碼:\r\n" + viewsHost + "/resetPassword?token=" + aesUtil.StrToHex(aesStr))) {
                map.put("status", 0);
                map.put("tip", "success");
            } else {
                map.put("status", -3);
                map.put("tip", "發送郵件失敗,請稍後再試");
            }
        } else {
            map.put("status", -3);
            map.put("tip", "請求失敗,請稍後再試");
        }
        return map;
    }

    @PassToken
    @RequestMapping("resetPassword")
    public Map<String, Object> resetPassword(String password, String token, String vall, String randstr) throws Exception {
        Map<String, Object> map = new HashMap<>();
        if (!tencentCloudApiUtil.codeResponse(util.getLocalIp(request), vall, randstr)) {
            map.put("status", -1);
            map.put("tip", "驗證碼錯誤");
            return map;
        }
        String[] tokens = aesUtil.decrypt(aesUtil.HexToStr(token), aesKey).split("<cut>");
        if (tokens.length != 4) {
            map.put("status", -2);
            map.put("tip", "該token無效");
            return map;
        }
        if (!new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tokens[2]).after(new Date())) {
            map.put("status", -3);
            map.put("tip", "該連接已過期,請重新獲取");
            return map;
        }
        User select = new User();
        select.setUsername(tokens[0]);
        select = userService.getByUsername(select);
        if (!tokens[3].equals(util.getMd5(select.getPassword()))) {
            map.put("status", -5);
            map.put("tip", "該連接已失效,請重新獲取");
            return map;
        }
        select.setPassword(password);
        select = userService.userLogin(select);
        if (select != null) {
            map.put("status", -6);
            map.put("tip", "新密碼不能與當前密碼一致");
            return map;
        }
        User user = new User();
        user.setUsername(tokens[0]);
        user.setPassword(password);
        int count = userService.updateByUsername(user);
        if (count > 0) {
            map.put("status", 0);
            map.put("tip", "success");
        } else {
            map.put("status", -4);
            map.put("tip", "修改失敗,請稍後再試");
        }
        return map;
    }

    @PassToken
    @RequestMapping("checkToken")
    public Map<String, Object> checkToken() {
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", new Date().getTime());
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            if (token.getExpire_time().after(new Date())) {
                User user = new User();
                user.setId(token.getId());
                user = userService.getById(user);
                if (user != null) {
                    if (user.getState() == 1) {
                        if (util.getMd5(user.getPassword()).equals(token.getPassword())) {
                            map.put("status", 0);
                            map.put("tip", "success");
                            map.put("username", token.getUsername());
                            map.put("ttl", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(token.getExpire_time()));
//                            if("1".equals(redisService.get("checkTokenByIp"))){
//                                if(!util.getMd5(otherService.getLocalIp(request)).equals(token.getIp())){
//                                    map.remove("username");
//                                    map.remove("ttl");
//                                    map.put("status",-1);
//                                    map.put("tip","用戶IP異常，請重新登錄");
//                                }
//                            }
                        } else {
                            map.put("status", -1);
                            map.put("tip", "用戶密碼錯誤");
                        }
                    } else {
                        map.put("status", -2);
                        map.put("tip", "用戶狀態異常");
                    }
                } else {
                    map.put("status", -3);
                    map.put("tip", "用戶不存在");
                }
            } else {
                map.put("status", -5);
                map.put("tip", "登錄已到期");
            }
        } else {
            map.put("status", -4);
            map.put("tip", "登錄已失效");
        }
        return map;
    }

    @LoginToken
    @RequestMapping("addCertificate")
    public Map<String, Object> addCertificate(String studentId, String realName, int department, String className, String photoUrl, String remark) {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(request.getHeader("Authorization"));
        if (token != null) {
            User user = new User();
            user.setId(token.getId());
            User select = userService.getById(user);
            if (select != null) {
                if (select.getVerified_state() == 1 || select.getVerified_state() == 4) {
                    user.setVerified_state(2);
                    user.setStudent_id(studentId);
                    user.setActual_name(realName);
                    user.setDepartment_id(department);
                    user.setClass_name(className);
                    user.setStudent_card(photoUrl);
                    user.setVerified_remark(remark);
                    if (userService.updateById(user) > 0) {
                        map.put("status", 0);
                        map.put("tip", "success");
                    } else {
                        map.put("status", -4);
                        map.put("tip", "提交失敗,請稍後再試");
                    }
                } else if (select.getVerified_state() == 2) {
                    map.put("status", -1);
                    map.put("tip", "您的資料正在審核中,請耐心等待");
                } else if (select.getVerified_state() == 3) {
                    map.put("status", -2);
                    map.put("tip", "您已經審核通過了,無需重複認證");
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

    @PassToken
    @RequestMapping("uploadCertificatePhoto")
    public Map<String, Object> uploadCertificatePhoto(@RequestParam("file") MultipartFile multipartFile, String authorization) throws Exception {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(authorization);
        if (token != null) {
            User user = new User();
            user.setId(token.getId());
            user = userService.getById(user);
            if (user != null) {
                String fileName = multipartFile.getOriginalFilename();
                if (multipartFile == null) {
                    map.put("status", -8);
                    map.put("tip", "文件為空");
                    return map;
                }
                if (fileName.lastIndexOf(".") < 0) {
                    map.put("status", -6);
                    map.put("tip", "文件格式錯誤");
                    return map;
                }
                String prefix = fileName.substring(fileName.lastIndexOf("."));
                if (!prefix.equalsIgnoreCase(".jpg") && !prefix.equalsIgnoreCase(".jpeg") && !prefix.equalsIgnoreCase(".bmp") && !prefix.equalsIgnoreCase(".png")) {
                    map.put("status", -7);
                    map.put("tip", "文件格式錯誤");
                    return map;
                }
                String filePath = "imagesFile-" + System.currentTimeMillis();
                final File excelFile = File.createTempFile(filePath, prefix);
                multipartFile.transferTo(excelFile);
                long size = excelFile.length() / 1024;
                if (size > 10 * 1024) {
                    map.put("status", -5);
                    map.put("tip", "文件大小超出限制");
                    return map;
                }
                Map<String, Object> cosUpload = new HashMap<>();
                String key = certificationPath + util.getMd5(user.getUsername() + "_certificatePhoto") + ".jpg";
                cosUpload = tencentCloudApiUtil.cosUpload(excelFile, key);
                if (!excelFile.delete()) {
                    System.out.println("delete fail:" + excelFile.getName());
                }
                if (cosUpload.get("tip").equals("success")) {
                    map.put("status", 0);
                    map.put("tip", "上傳成功");
                    map.put("url", cosUpload.get("url"));
                } else {
                    map.put("status", -2);
                    map.put("tip", "上傳失敗,請稍後再試");
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

    @PassToken
    @RequestMapping("activationUser")
    public Map<String, Object> activationUser(String token) throws Exception {
        Map<String, Object> map = new HashMap<>();
        if (token == null || token == "") {
            map.put("status", -2);
            map.put("tip", "該token無效");
            return map;
        }
        String username = aesUtil.decrypt(aesUtil.HexToStr(token), aesKey);
        if (username == null || username == "") {
            map.put("status", -2);
            map.put("tip", "該token已失效");
            return map;
        }
        User select = new User();
        select.setUsername(username);
        select = userService.getByUsername(select);
        if (select == null) {
            map.put("status", -5);
            map.put("tip", "該連接已失效,請重新註冊");
            return map;
        }
        if (select.getState() != 5) {
            map.put("status", 0);
            map.put("tip", "該賬號已激活無需重複激活");
            return map;
        }
        User user = new User();
        user.setUsername(username);
        user.setState(1);
        int count = userService.updateByUsername(user);
        if (count > 0) {
            map.put("status", 0);
            map.put("tip", "激活成功");
        } else {
            map.put("status", -4);
            map.put("tip", "激活失敗,請稍後再試");
        }
        return map;
    }

    @PassToken
    @RequestMapping("uploadAvatar")
    public Map<String, Object> uploadAvatar(@RequestParam("file") MultipartFile multipartFile, String authorization) throws Exception {
        Map<String, Object> map = new HashMap<>();
        Token token = new Token();
        token = tokenService.parseToken(authorization);
        if (token != null) {
            User user = new User();
            user.setId(token.getId());
            user = userService.getById(user);
            if (user != null) {
                String fileName = multipartFile.getOriginalFilename();
                if (multipartFile == null) {
                    map.put("status", -8);
                    map.put("tip", "文件為空");
                    return map;
                }
                if (fileName.lastIndexOf(".") < 0) {
                    map.put("status", -6);
                    map.put("tip", "文件格式錯誤");
                    return map;
                }
                String prefix = fileName.substring(fileName.lastIndexOf("."));
                if (!prefix.equalsIgnoreCase(".jpg") && !prefix.equalsIgnoreCase(".jpeg") && !prefix.equalsIgnoreCase(".bmp") && !prefix.equalsIgnoreCase(".png")) {
                    map.put("status", -7);
                    map.put("tip", "文件格式錯誤");
                    return map;
                }
                String filePath = "imagesFile-" + System.currentTimeMillis();
                final File excelFile = File.createTempFile(filePath, prefix);
                multipartFile.transferTo(excelFile);
                long size = excelFile.length() / 1024;
                if (size > 10 * 1024) {
                    map.put("status", -5);
                    map.put("tip", "文件大小超出限制");
                    return map;
                }
                Map<String, Object> cosUpload = new HashMap<>();
                String key = avatarPath + util.getMd5(user.getUsername() + "_avatar") + ".png";
                cosUpload = tencentCloudApiUtil.cosUpload(excelFile, key);
                if (!excelFile.delete()) {
                    System.out.println("delete fail:" + excelFile.getName());
                }
                if (cosUpload.get("tip").equals("success")) {
                    map.put("status", 0);
                    map.put("tip", "上傳成功");
                    map.put("url", cosUpload.get("url"));
                } else {
                    map.put("status", -2);
                    map.put("tip", "上傳失敗,請稍後再試");
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
}
