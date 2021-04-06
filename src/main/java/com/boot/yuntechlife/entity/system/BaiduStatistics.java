package com.boot.yuntechlife.entity.system;

import lombok.Data;

import java.util.Date;

/**
 * @Author: skwen
 * @ClassName: BaiduStatistics
 * @Description: 實體類
 * @Date: 2020-03-14
 */
@Data
public class BaiduStatistics {
    private int id;
    private String site_id;
    private Date date;
    private int pv_count;
    private float pv_ratio;
    private int visit_count;
    private int visitor_count;
    private int new_visitor_count;
    private float new_visitor_ratio;
    private int ip_count;
    private float bounce_ratio;
    private int avg_visit_time;
    private float avg_visit_pages;
    private boolean deleted;
    private Date add_time;
}
