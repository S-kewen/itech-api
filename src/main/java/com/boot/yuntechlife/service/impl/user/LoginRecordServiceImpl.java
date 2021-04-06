package com.boot.yuntechlife.service.impl.user;

import com.boot.yuntechlife.dao.user.LoginRecordMapper;
import com.boot.yuntechlife.dao.user.UserMapper;
import com.boot.yuntechlife.entity.user.LoginRecord;
import com.boot.yuntechlife.entity.user.User;
import com.boot.yuntechlife.service.user.LoginRecordService;
import com.boot.yuntechlife.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author: skwen
 * @ClassName: LoginRecordServiceImpl
 * @Description: Service
 * @Date: 2020-03-08
 */
@Component
public class LoginRecordServiceImpl implements LoginRecordService {
    @Autowired
    private LoginRecordMapper loginRecordMapper;

    @Override
    public LoginRecord getByIp(LoginRecord loginRecord) {
        return loginRecordMapper.getByIp(loginRecord);
    }

    @Override
    public int insertOne(LoginRecord loginRecord) {
        return loginRecordMapper.insertOne(loginRecord);
    }

    @Override
    public List<LoginRecord> getList(User user) {
        return loginRecordMapper.getList(user);
    }

    @Override
    public int deleteOne(LoginRecord loginRecord) {
        return loginRecordMapper.deleteOne(loginRecord);
    }

    @Override
    public int getCount(LoginRecord loginRecord) {
        return loginRecordMapper.getCount(loginRecord);
    }

    @Override
    public List<Map<String, Object>> getCountByWeek(LoginRecord loginRecord) {
        return loginRecordMapper.getCountByWeek(loginRecord);
    }
}
