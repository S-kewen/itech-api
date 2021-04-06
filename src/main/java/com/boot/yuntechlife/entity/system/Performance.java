package com.boot.yuntechlife.entity.system;

import lombok.Data;

import java.util.Date;

/**
 * @Author: skwen
 * @ClassName: Performance
 * @Description: 實體類
 * @Date: 2020-04-07
 */
@Data
public class Performance {
    private int id;
    private int service_id;
    private float cpu;
    private float ram;
    private boolean deleted;
    private Date add_time;
}
