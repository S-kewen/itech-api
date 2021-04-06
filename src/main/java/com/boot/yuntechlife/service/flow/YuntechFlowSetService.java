package com.boot.yuntechlife.service.flow;

import com.boot.yuntechlife.entity.flow.YuntechFlow;
import com.boot.yuntechlife.entity.flow.YuntechFlowSet;
import com.boot.yuntechlife.entity.user.User;

import java.util.List;

public interface YuntechFlowSetService {
    int insertOne(YuntechFlowSet yuntechFlowSet);

    List<YuntechFlowSet> getList(User user);

    int deleteOne(YuntechFlowSet yuntechFlowSet);

    int getCount(YuntechFlowSet yuntechFlowSet);

    int updateOne(YuntechFlowSet yuntechFlowSet);

    YuntechFlowSet getById(YuntechFlowSet yuntechFlowSet);
}
