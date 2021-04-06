package com.boot.yuntechlife.service.impl.system;

import com.boot.yuntechlife.dao.system.SystemLogMapper;
import com.boot.yuntechlife.entity.system.SystemLog;
import com.boot.yuntechlife.service.system.SystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: skwen
 * @ClassName: SystemLogServiceImpl
 * @Description: Service
 * @Date: 2020-04-06
 */
@Component
public class SystemLogServiceImpl implements SystemLogService {
    @Autowired
    private SystemLogMapper systemLogMapper;
    @Override
    public int insertOne(SystemLog systemLog) {
        return systemLogMapper.insertOne(systemLog);
    }

}

