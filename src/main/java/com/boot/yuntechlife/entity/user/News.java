package com.boot.yuntechlife.entity.user;

import lombok.Data;

import java.util.Date;

/**
 * @Author: skwen
 * @ClassName: News
 * @Description: News實體類
 * @Date: 2020-03-08
 */
@Data
public class News {
    private int id;
    private int user_id;
    private int state;
    private int type;
    private String sender;
    private String title;
    private String msg;
    private boolean deleted;
    private Date add_time;
}
