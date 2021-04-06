package com.boot.yuntechlife.service.system;

import com.boot.yuntechlife.entity.system.BaiduStatistics;
import com.boot.yuntechlife.entity.system.Feedback;

import java.util.List;

public interface FeedbackService {
    int insertOne(Feedback feedback);

    int getCount(Feedback feedback);
}
