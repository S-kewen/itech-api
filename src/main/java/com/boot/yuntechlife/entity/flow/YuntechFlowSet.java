package com.boot.yuntechlife.entity.flow;

import lombok.Data;

import java.util.Date;

/**
 * @Author: skwen
 * @ClassName: YuntechFlowSet
 * @Description: 實體類
 * @Date: 2020-03-10
 */
@Data
public class YuntechFlowSet {
    private int id;
    private int user_id;
    private String ip;
    private int state;
    private double warn_value;
    private String warn_mail;
    private int mail_state;
    private int mail_interval;
    private String warn_phone;
    private int phone_state;
    private int phone_interval;
    private String remark;
    private boolean deleted;
    private Date add_time;
}
