package com.boot.yuntechlife.dao.flow;

import com.boot.yuntechlife.entity.flow.YuntechFlowSet;
import com.boot.yuntechlife.entity.flow.YuntechFlowWarn;
import com.boot.yuntechlife.entity.user.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: skwen
 * @ClassName: YuntechFlowWarnMapper
 * @Description: dao
 * @Date: 2020-03-10
 */
@Mapper
public interface YuntechFlowWarnMapper {
    int insertOne(YuntechFlowWarn yuntechFlowWarn);

    List<YuntechFlowWarn> getList(User user);

    int deleteOne(YuntechFlowWarn yuntechFlowWarn);

    int updateOne(YuntechFlowWarn yuntechFlowWarn);

    List<YuntechFlowWarn> disposeWarn(YuntechFlowWarn yuntechFlowWarn);
}