package com.boot.yuntechlife.service.impl.home;

import com.boot.yuntechlife.dao.home.ConsoleMapper;
import com.boot.yuntechlife.entity.home.Console;
import com.boot.yuntechlife.entity.user.User;
import com.boot.yuntechlife.service.home.ConsoleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: skwen
 * @ClassName: ConsoleInfoServiceImpl
 * @Description: ConsoleInfoService
 * @Date: 2020-03-00
 */
@Component
public class ConsoleInfoServiceImpl implements ConsoleInfoService {
    @Autowired
    private ConsoleMapper consoleInfoMapper;

    @Override
    public Console getByUserId(User user) {
        return consoleInfoMapper.getByUserId(user);
    }
}
