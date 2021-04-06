package com.boot.yuntechlife.entity.user;

import lombok.Data;

import java.util.Date;

/**
 * @Author: skwen
 * @ClassName: RechargeRecord
 * @Description: 實體類
 * @Date: 2020-03-14
 */
@Data
public class RechargeRecord {
    private int id;
    private int user_id;
    private int state;
    private int type;
    private int card_id;
    private boolean deleted;
    private Date add_time;
}
