package com.boot.yuntechlife.entity.system;

import lombok.Data;

import java.util.Date;

/**
 * @Author: skwen
 * @ClassName: Feedback
 * @Description: 實體類
 * @Date: 2020-03-15
 */
@Data
public class Feedback {
    private int id;
    private int user_id;
    private int state;
    private int type;
    private int anonymous;
    private String content;
    private String reply;
    private boolean deleted;
    private Date add_time;
}
