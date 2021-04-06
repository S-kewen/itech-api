package com.boot.yuntechlife.entity.flow;

import lombok.Data;

import java.util.Date;

/**
 * @Author: skwen
 * @ClassName: YuntechFlow
 * @Description: 實體類
 * @Date: 2020-03-10
 */
@Data
public class YuntechFlow {
    private int id;
    private String ip;
    private double ins_up;
    private double ins_down;
    private double ext_up;
    private double ext_down;
    private double flow;
    private double ratio;
    private boolean deleted;
    private Date add_time;
}
