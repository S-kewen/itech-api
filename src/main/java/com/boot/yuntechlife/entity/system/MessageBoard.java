package com.boot.yuntechlife.entity.system;

import lombok.Data;

import java.util.Date;

/**
 * @Author: skwen
 * @ClassName: MessageBoard
 * @Description: 實體類
 * @Date: 2020-03-15
 */
@Data
public class MessageBoard {
    private int id;
    private int user_id;
    private int state;
    private int anonymous;
    private String msg;
    private String ip;
    private String system;
    private String browser;
    private String header;
    private boolean deleted;
    private Date add_time;
}
