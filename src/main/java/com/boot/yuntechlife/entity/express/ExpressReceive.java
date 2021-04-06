package com.boot.yuntechlife.entity.express;

import lombok.Data;

import java.util.Date;

/**
 * @Author: skwen
 * @ClassName: ExpressReceive
 * @Description: 實體類
 * @Date: 2020-03-16
 */
@Data
public class ExpressReceive {
    private int id;
    private int user_id;
    private int express_taker_id;
    private int state;
    private String real_name;
    private String line;
    private String phone;
    private boolean deleted;
    private Date add_time;
}
