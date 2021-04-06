package com.boot.yuntechlife.service.impl.system;

import com.boot.yuntechlife.dao.system.BaiduStatisticsMapper;
import com.boot.yuntechlife.dao.system.FeedbackMapper;
import com.boot.yuntechlife.entity.system.BaiduStatistics;
import com.boot.yuntechlife.entity.system.Feedback;
import com.boot.yuntechlife.service.system.BaiduStatisticsService;
import com.boot.yuntechlife.service.system.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: skwen
 * @ClassName: FeedbackServiceImpl
 * @Description: Service
 * @Date: 2020-03-15
 */
@Component
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackMapper feedbackMapper;

    @Override
    public int insertOne(Feedback feedback) {
        return feedbackMapper.insertOne(feedback);
    }

    @Override
    public int getCount(Feedback feedback) {
        return feedbackMapper.getCount(feedback);
    }

}

