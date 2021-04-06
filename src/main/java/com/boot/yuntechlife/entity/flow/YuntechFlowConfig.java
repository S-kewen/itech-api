package com.boot.yuntechlife.entity.flow;

import lombok.Data;

import java.util.Date;

/**
 * @Author: skwen
 * @ClassName: YuntechFlowConfig
 * @Description: 實體類
 * @Date: 2020-03-12
 */
@Data
public class YuntechFlowConfig {
    private int id;
    private int user_id;
    private int state;
    private int max_enable;
    private String remark;
    private boolean deleted;
    private Date add_time;
}
