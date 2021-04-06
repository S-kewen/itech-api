package com.boot.yuntechlife.dao.system;

import com.boot.yuntechlife.entity.system.BaiduStatistics;
import com.boot.yuntechlife.entity.system.Performance;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: skwen
 * @ClassName: PerformanceMapper
 * @Description: dao
 * @Date: 2020-04-07
 */
@Mapper
public interface PerformanceMapper {
    int insertOne(Performance performance);
}