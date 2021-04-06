package com.boot.yuntechlife.dao.system;

import com.boot.yuntechlife.entity.system.BaiduStatistics;
import com.boot.yuntechlife.entity.user.LoginRecord;
import com.boot.yuntechlife.entity.user.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: skwen
 * @ClassName:BaiduStatisticsMapper
 * @Description: dao
 * @Date: 2020-03-14
 */
@Mapper
public interface BaiduStatisticsMapper {
    int insertOne(BaiduStatistics baiduStatistics);
    List<BaiduStatistics> getListByWeek(BaiduStatistics baiduStatistics);
}