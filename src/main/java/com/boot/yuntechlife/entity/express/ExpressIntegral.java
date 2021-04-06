package com.boot.yuntechlife.entity.express;

import lombok.Data;

import java.util.Date;

/**
 * @Author: skwen
 * @ClassName: ExpressIntegral
 * @Description: 實體類
 * @Date: 2020-03-16
 */
@Data
public class ExpressIntegral {
    private int id;
    private int user_id;
    private int state;
    private float grade;
    private boolean deleted;
    private Date add_time;
}
