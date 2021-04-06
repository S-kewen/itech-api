package com.boot.yuntechlife.dao.system;

import com.boot.yuntechlife.entity.system.BaiduStatistics;
import com.boot.yuntechlife.entity.system.Feedback;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: skwen
 * @ClassName:FeedbackMapper
 * @Description: dao
 * @Date: 2020-03-15
 */
@Mapper
public interface FeedbackMapper {
    int insertOne(Feedback feedback);

    int getCount(Feedback feedback);
}