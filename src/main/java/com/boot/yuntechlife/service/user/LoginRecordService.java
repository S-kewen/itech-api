package com.boot.yuntechlife.service.user;

import com.boot.yuntechlife.entity.user.LoginRecord;
import com.boot.yuntechlife.entity.user.User;

import java.util.List;
import java.util.Map;

public interface LoginRecordService {
    LoginRecord getByIp(LoginRecord loginRecord);

    int insertOne(LoginRecord loginRecord);

    List<LoginRecord> getList(User user);

    int deleteOne(LoginRecord loginRecord);

    int getCount(LoginRecord loginRecord);

    List<Map<String, Object>> getCountByWeek(LoginRecord loginRecord);
}
