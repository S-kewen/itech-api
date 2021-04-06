package com.boot.yuntechlife.entity.user;

import lombok.Data;

import java.util.Date;

/**
 * @Author: skwen
 * @ClassName: Card
 * @Description: 實體類
 * @Date: 2020-03-14
 */
@Data
public class Card {
    private int id;
    private int user_id;
    private int state;
    private int type;
    private String cdkey;
    private float amount;
    private Date vaild_time;
    private String remark;
    private boolean deleted;
    private Date add_time;
}
