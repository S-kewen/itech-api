package com.boot.yuntechlife.entity.flow;

import lombok.Data;

import java.util.Date;

/**
 * @Author: skwen
 * @ClassName: YuntechFlowWarn
 * @Description: 實體類
 * @Date: 2020-03-10
 */
@Data
public class YuntechFlowWarn {
    private int id;
    private int user_id;
    private String ip;
    private double warn_value;
    private double real_value;
    private String warn_mail;
    private int mail_state;
    private String warn_phone;
    private int phone_state;
    private Date complete_time;
    private boolean deleted;
    private Date add_time;
}
