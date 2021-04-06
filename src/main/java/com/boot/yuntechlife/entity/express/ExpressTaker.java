package com.boot.yuntechlife.entity.express;

import lombok.Data;

import java.util.Date;

/**
 * @Author: skwen
 * @ClassName: ExpressTaker
 * @Description: 實體類
 * @Date: 2020-03-15
 */
@Data
public class ExpressTaker {
    private int id;
    private int user_id;
    private int state;
    private int type;
    private String contact_name;
    private String line;
    private String phone;
    private Date appointment_time;
    private String appointment_points;
    private int express_type;
    private float amount;
    private float commission;
    private float surcharge;
    private float actual_amount;
    private int take_points;
    private String express_name;
    private String express_num;
    private String recipient_name;
    private String recipient_phone;
    private String remark;
    private Date complete_time;
    private boolean deleted;
    private Date add_time;
}
