package com.boot.yuntechlife.entity.school;

import lombok.Data;

import java.util.Date;

/**
 * @Author: skwen
 * @ClassName: YuntechExpress
 * @Description: 實體類
 * @Date: 2020-03-18
 */
@Data
public class YuntechExpress {
    private int id;
    private int state;
    private int region;
    private String express_name;
    private String express_num;
    private String dormitory;
    private String addressee;
    private Date receive_time;
    private Date receipt_time;
    private boolean deleted;
    private Date add_time;
}
