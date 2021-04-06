package com.boot.yuntechlife.service.impl.user;

import com.boot.yuntechlife.dao.user.UserMapper;
import com.boot.yuntechlife.entity.user.LoginRecord;
import com.boot.yuntechlife.entity.user.User;
import com.boot.yuntechlife.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author: skwen
 * @ClassName: UserServiceImpl
 * @Description: userService
 * @Date: 2020-03-08
 */
@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getById(User user) {
        return userMapper.getById(user);
    }

    @Override
    public User getByUsername(User user) {
        return userMapper.getByUsername(user);
    }

    @Override
    public User userLogin(User user) {
        return userMapper.userLogin(user);
    }

    @Override
    public int updateById(User user) {
        return userMapper.updateById(user);
    }

    @Override
    public int insertOne(User user) {
        return userMapper.insertOne(user);
    }

    @Override
    public int getCount(User user) {
        return userMapper.getCount(user);
    }

    @Override
    public int updateByUsername(User user) {
        return userMapper.updateByUsername(user);
    }

    @Override
    public List<Map<String, Object>> getCountByWeek(User user) {
        return userMapper.getCountByWeek(user);
    }
}
