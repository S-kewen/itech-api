package com.boot.yuntechlife.entity.system;

import lombok.Data;

import java.util.Date;

/**
 * @Author: skwen
 * @ClassName: Notice
 * @Description: 實體類
 * @Date: 2020-03-17
 */
@Data
public class Notice {
    private int id;
    private int user_id;
    private int state;
    private int type;
    private int top;
    private String title;
    private Date show_time;
    private Date hide_time;
    private int jump_type;
    private String lay_text;
    private String url;
    private String content;
    private boolean deleted;
    private Date add_time;
}
