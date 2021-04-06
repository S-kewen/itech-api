package com.boot.yuntechlife.dao.express;

import com.boot.yuntechlife.entity.express.ExpressReceive;
import com.boot.yuntechlife.entity.express.ExpressTaker;
import com.boot.yuntechlife.entity.user.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author: skwen
 * @ClassName: ExpressTakerMapper
 * @Description: dao
 * @Date: 2020-03-16
 */
@Mapper
public interface ExpressReceiveMapper {
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