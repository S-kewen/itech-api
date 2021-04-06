package com.boot.yuntechlife.entity.home;

import lombok.Data;

/**
 * @Author: skwen
 * @ClassName: ConsoleInfo
 * @Description: 控制台屬性實體類
 * @Date: 2020-03-09
 */
@Data
public class Console {
    private float todayFlow;
    private float todayIncome;
    private int unCompletedNum;
    private int completedNum;
    private int unExpressNum;
    private int expressNum;
    private int verifiedState;
}
