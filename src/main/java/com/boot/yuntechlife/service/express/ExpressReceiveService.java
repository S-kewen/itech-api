package com.boot.yuntechlife.service.express;

import com.boot.yuntechlife.entity.express.ExpressReceive;
import com.boot.yuntechlife.entity.express.ExpressTaker;
import com.boot.yuntechlife.entity.user.User;

import java.util.List;
import java.util.Map;

public interface ExpressReceiveService {
    int insertOne(ExpressReceive expressReceive);

    int getCount(ExpressReceive expressReceive);

    List<Map<String, Object>> getList(User user);

    int deleteOne(ExpressReceive expressReceive);

    int updateOne(ExpressReceive expressReceive);

    ExpressReceive getById(ExpressReceive expressReceive);

    int getCountByPending(ExpressReceive expressReceive);

    ExpressReceive getByExpressTakerId(ExpressReceive expressReceive);

    ExpressReceive getByIssuance(ExpressReceive expressReceive);

    int getCountByTotal(ExpressReceive expressReceive);
}
