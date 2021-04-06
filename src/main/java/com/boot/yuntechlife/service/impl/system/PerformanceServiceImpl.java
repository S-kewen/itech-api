package com.boot.yuntechlife.service.impl.system;

import com.boot.yuntechlife.dao.system.BaiduStatisticsMapper;
import com.boot.yuntechlife.dao.system.PerformanceMapper;
import com.boot.yuntechlife.entity.system.BaiduStatistics;
import com.boot.yuntechlife.entity.system.Performance;
import com.boot.yuntechlife.service.system.BaiduStatisticsService;
import com.boot.yuntechlife.service.system.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: skwen
 * @ClassName: LoginRecordServiceImpl
 * @Description: Service
 * @Date: 2020-03-08
 */
@Component
public class PerformanceServiceImpl implements PerformanceService {
    @Autowired
    private PerformanceMapper performanceMapper;

    @Override
    public int insertOne(Performance performance) {
        return performanceMapper.insertOne(performance);
    }


}
