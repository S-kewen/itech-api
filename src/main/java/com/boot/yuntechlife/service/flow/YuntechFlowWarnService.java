package com.boot.yuntechlife.service.flow;

import com.boot.yuntechlife.entity.flow.YuntechFlowSet;
import com.boot.yuntechlife.entity.flow.YuntechFlowWarn;
import com.boot.yuntechlife.entity.user.User;

import java.util.List;

public interface YuntechFlowWarnService {
    int insertOne(YuntechFlowWarn yuntechFlowWarn);

    List<YuntechFlowWarn> getList(User user);

    int deleteOne(YuntechFlowWarn yuntechFlowWarn);

    int updateOne(YuntechFlowWarn yuntechFlowWarn);

    List<YuntechFlowWarn> disposeWarn(YuntechFlowWarn yuntechFlowWarn);
}
