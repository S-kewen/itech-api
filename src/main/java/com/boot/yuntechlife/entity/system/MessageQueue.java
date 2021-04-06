package com.boot.yuntechlife.entity.system;

import lombok.Data;

import java.util.Date;

/**
 * @Author: skwen
 * @ClassName: MessageQueue
 * @Description: 實體類
 * @Date: 2020-03-17
 */
@Data
public class MessageQueue {
    private int id;
    private int user_id;
    private int state;
    private int type;
    private int retry_num;
    private int try_num;
    private String to;
    private String title;
    private String msg;
    private String remark;
    private Date complete_time;
    private boolean deleted;
    private Date add_time;
}
