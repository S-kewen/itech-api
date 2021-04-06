package com.boot.yuntechlife.dao.flow;

import com.boot.yuntechlife.entity.flow.YuntechFlow;
import com.boot.yuntechlife.entity.flow.YuntechFlowSet;
import com.boot.yuntechlife.entity.user.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: skwen
 * @ClassName: YuntechFlowSetMapper
 * @Description: dao
 * @Date: 2020-03-10
 */
@Mapper
public interface YuntechFlowSetMapper {
    int insertOne(YuntechFlowSet yuntechFlowSet);

    List<YuntechFlowSet> getList(User user);

    int deleteOne(YuntechFlowSet yuntechFlowSet);

    int getCount(YuntechFlowSet yuntechFlowSet);

    int updateOne(YuntechFlowSet yuntechFlowSet);

    YuntechFlowSet getById(YuntechFlowSet yuntechFlowSet);
}