package com.boot.yuntechlife.service.impl.user;

import com.boot.yuntechlife.dao.user.LoginRecordMapper;
import com.boot.yuntechlife.dao.user.UserIndexMapper;
import com.boot.yuntechlife.entity.user.LoginRecord;
import com.boot.yuntechlife.entity.user.User;
import com.boot.yuntechlife.entity.user.UserIndex;
import com.boot.yuntechlife.service.user.LoginRecordService;
import com.boot.yuntechlife.service.user.UserIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: skwen
 * @ClassName: UserIndexServiceImpl
 * @Description: userIndexService
 * @Date: 2020-03-09
 */
@Component
public class UserIndexServiceImpl implements UserIndexService {
    @Autowired
    private UserIndexMapper userIndexMapper;

    @Override
    public UserIndex getByUserId(User user) {
        return userIndexMapper.getByUserId(user);
    }
}
