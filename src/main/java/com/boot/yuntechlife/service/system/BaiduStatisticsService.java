package com.boot.yuntechlife.service.system;

import com.boot.yuntechlife.entity.system.BaiduStatistics;
import com.boot.yuntechlife.entity.user.LoginRecord;
import com.boot.yuntechlife.entity.user.User;

import java.util.List;

public interface BaiduStatisticsService {
    int insertOne(BaiduStatistics baiduStatistics);

    List<BaiduStatistics> getListByWeek(BaiduStatistics baiduStatistics);
}
