package com.boot.yuntechlife.entity.user;

import lombok.Data;

import java.util.Date;

/**
 * @Author: skwen
 * ClassName: UserIndex
 * @Description: UserIndex實體類
 * @Date: 2020-03-08
 */
@Data
public class UserIndex {
    private float todayIncome;
    private float alldayIncome;
    private String actual_name;
    private String balance;
    private int department_id;
    private String class_name;
    private String line;
    private String email;
    private String phone;
    private int verified_state;
    private float todayFlow;
    private float allFlow;
    private int warnNum;
    private int processing;
    private int pending;
    private int totalOrders;
}
