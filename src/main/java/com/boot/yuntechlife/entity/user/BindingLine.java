package com.boot.yuntechlife.entity.user;

import lombok.Data;

import java.util.Date;

/**
 * @Author: skwen
 * @ClassName: BindingLine
 * @Description: 實體類
 * @Date: 2020-03-24
 */
@Data
public class BindingLine {
    private int id;
    private int user_id;
    private int state;
    private int type;
    private String language;
    private String view_type;
    private String line_user_id;
    private String utou_id;
    private String room_id;
    private String group_id;
    private String ip;
    private String system;
    private String browser;
    private String header;
    private boolean deleted;
    private Date add_time;
}
