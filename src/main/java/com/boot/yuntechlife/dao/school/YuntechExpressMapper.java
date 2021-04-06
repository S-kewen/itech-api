package com.boot.yuntechlife.dao.school;

import com.boot.yuntechlife.entity.school.YuntechExpress;
import com.boot.yuntechlife.entity.user.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: skwen
 * @ClassName: YuntechExpressMapper
 * @Description: dao
 * @Date: 2020-03-18
 */
@Mapper
public interface YuntechExpressMapper {
    int insertOne(YuntechExpress yuntechExpress);

    int getCount(YuntechExpress yuntechExpress);

    int updateOne(YuntechExpress yuntechExpress);

    List<YuntechExpress> getList(User user);

    List<YuntechExpress> getListByTask(User user);
}