package com.boot.yuntechlife.entity.system;

import lombok.Data;

import java.util.Date;

/**
 * @Author: skwen
 * @ClassName: RunRecord
 * @Description: 實體類
 * @Date: 2020-04-06
 */
@Data
public class SystemLog {
    private int id;
    private int state;
    private int type;
    private int service_id;
    private String pc_name;
    private String ip;
    private String msg;
    private String remark;
    private boolean deleted;
    private Date add_time;
    //以上來自sql
    private int [] ids;
}
