package com.boot.yuntechlife.entity.user;

import lombok.Data;

import java.util.Date;

/**
 * @Author: skwen
 * @ClassName: User
 * @Description: User實體類
 * @Date: 2020-03-08
 */
@Data
public class User {
    private int id;
    private String username;
    private String password;
    private String salt;
    private int state;
    private Date release_time;
    private String email;
    private String phone;
    private String line;
    private float balance;
    private String student_id;
    private String actual_name;
    private int department_id;
    private String class_name;
    private String student_card;
    private int verified_state;
    private String verified_remark;
    private String verified_explain;
    private String avatar;
    private String remark;
    private Date add_time;
    //以上來自sql
    private String sortName;
    private String sortOrder;
    private String keyword;
    private int type;
    private Date startTime;
    private Date endTime;
    private String express_name;
    private int express_type;
    private int take_points;
    private int region;
}
