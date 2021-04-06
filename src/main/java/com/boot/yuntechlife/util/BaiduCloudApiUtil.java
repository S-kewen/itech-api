package com.boot.yuntechlife.util;

import com.boot.yuntechlife.entity.system.BaiduStatistics;

import java.io.IOException;
import java.text.ParseException;

public interface BaiduCloudApiUtil {
    BaiduStatistics getStatisticsInfo() throws ParseException;
}
