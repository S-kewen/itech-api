package com.boot.yuntechlife.dao.flow;

import com.boot.yuntechlife.entity.flow.YuntechFlow;
import com.boot.yuntechlife.entity.user.Transaction;
import com.boot.yuntechlife.entity.user.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: skwen
 * @ClassName: YuntechFlowMapper
 * @Description: dao
 * @Date: 2020-03-10
 */
@Mapper
public interface YuntechFlowMapper {
    int insertOne(YuntechFlow yuntechFlow);

    List<YuntechFlow> getList(User user);

    int deleteOne(YuntechFlow yuntechFlow);

    List<YuntechFlow> getDayInfoByWeek(YuntechFlow yuntechFlow);

    YuntechFlow getSumByIp(YuntechFlow yuntechFlow);

    int getDayNumByIp(YuntechFlow yuntechFlow);

    List<YuntechFlow> getListByDay(User user);

    YuntechFlow getByIp(YuntechFlow yuntechFlow);
}