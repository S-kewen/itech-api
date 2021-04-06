package com.boot.yuntechlife.entity.user;

import lombok.Data;

import java.util.Date;

/**
 * @Author: skwen
 * @ClassName: Transaction
 * @Description: 實體類
 * @Date: 2020-03-10
 */
@Data
public class Transaction {
    private int id;
    private int user_id;
    private int state;
    private int type;
    private String title;
    private float amount;
    private float commission;
    private float actual_amount;
    private float balance;
    private boolean deleted;
    private Date add_time;
}
