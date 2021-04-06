package com.boot.yuntechlife.entity.user;

import lombok.Data;

import java.util.Date;

/**
 * @Author: skwen
 * @ClassName: LoginRecord
 * @Description: LoginRecord實體類
 * @Date: 2020-03-08
 */
@Data
public class LoginRecord {
    private int id;
    private int user_id;
    private int state;
    private int type;
    private String ip;
    private String position;
    private float longitude;
    private float latitude;
    private String system;
    private String browser;
    private String header;
    private String token;
    private boolean deleted;
    private Date add_time;
}
