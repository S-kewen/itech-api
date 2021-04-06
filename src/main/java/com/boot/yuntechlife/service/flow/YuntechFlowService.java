package com.boot.yuntechlife.service.flow;

import com.boot.yuntechlife.entity.flow.YuntechFlow;
import com.boot.yuntechlife.entity.flow.YuntechFlowSet;
import com.boot.yuntechlife.entity.user.Transaction;
import com.boot.yuntechlife.entity.user.User;

import java.util.List;

public interface YuntechFlowService {
    int insertOne(YuntechFlow yuntechFlow);

    List<YuntechFlow> getList(User user);

    int deleteOne(YuntechFlow yuntechFlow);

    List<YuntechFlow> getDayInfoByWeek(YuntechFlow yuntechFlow);

    YuntechFlow getSumByIp(YuntechFlow yuntechFlow);

    int getDayNumByIp(YuntechFlow yuntechFlow);

    List<YuntechFlow> getListByDay(User user);

    YuntechFlow getByIp(YuntechFlow yuntechFlow);
}
