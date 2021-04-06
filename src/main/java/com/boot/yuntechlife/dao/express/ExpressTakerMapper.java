package com.boot.yuntechlife.dao.express;

import com.boot.yuntechlife.entity.express.ExpressTaker;
import com.boot.yuntechlife.entity.user.News;
import com.boot.yuntechlife.entity.user.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: skwen
 * @ClassName: ExpressTakerMapper
 * @Description: dao
 * @Date: 2020-03-15
 */
@Mapper
public interface ExpressTakerMapper {
    int insertOne(ExpressTaker expressTaker);

    int getCount(ExpressTaker expressTaker);

    List<ExpressTaker> getList(User user);

    int deleteOne(ExpressTaker expressTaker);

    int updateOne(ExpressTaker expressTaker);

    ExpressTaker getById(ExpressTaker expressTaker);

    List<ExpressTaker> getListByMarket(User user);

    ExpressTaker getByReceive(ExpressTaker expressTaker);
}