package com.boot.yuntechlife.service.school;

import com.boot.yuntechlife.entity.school.YuntechExpress;
import com.boot.yuntechlife.entity.user.User;

import java.util.List;

public interface YuntechExpressService {
    int insertOne(YuntechExpress yuntechExpress);

    int getCount(YuntechExpress yuntechExpress);

    int updateOne(YuntechExpress yuntechExpress);

    List<YuntechExpress> getList(User user);

    List<YuntechExpress> getListByTask(User user);
}
