package com.boot.yuntechlife.service.impl.system;

import com.boot.yuntechlife.dao.system.BaiduStatisticsMapper;
import com.boot.yuntechlife.dao.user.LoginRecordMapper;
import com.boot.yuntechlife.entity.system.BaiduStatistics;
import com.boot.yuntechlife.entity.user.LoginRecord;
import com.boot.yuntechlife.entity.user.User;
import com.boot.yuntechlife.service.system.BaiduStatisticsService;
import com.boot.yuntechlife.service.user.LoginRecordService;
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
public class BaiduStatisticsServiceImpl implements BaiduStatisticsService {
    @Autowired
    private BaiduStatisticsMapper baiduStatisticsMapper;

    @Override
    public int insertOne(BaiduStatistics baiduStatistics) {
        return baiduStatisticsMapper.insertOne(baiduStatistics);
    }

    @Override
    public List<BaiduStatistics> getListByWeek(BaiduStatistics baiduStatistics) {
        return baiduStatisticsMapper.getListByWeek(baiduStatistics);
    }


}
