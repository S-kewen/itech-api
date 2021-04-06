package com.boot.yuntechlife.service.express;

import com.boot.yuntechlife.entity.express.ExpressTaker;
import com.boot.yuntechlife.entity.user.News;
import com.boot.yuntechlife.entity.user.User;

import java.util.List;

public interface ExpressTakerService {
    int insertOne(ExpressTaker expressTaker);

    int getCount(ExpressTaker expressTaker);

    List<ExpressTaker> getList(User user);

    int deleteOne(ExpressTaker expressTaker);

    int updateOne(ExpressTaker expressTaker);

    ExpressTaker getById(ExpressTaker expressTaker);

    List<ExpressTaker> getListByMarket(User user);

    ExpressTaker getByReceive(ExpressTaker expressTaker);
}
