package com.boot.yuntechlife.dao.flow;

import com.boot.yuntechlife.entity.flow.YuntechFlowConfig;
import com.boot.yuntechlife.entity.flow.YuntechFlowSet;
import com.boot.yuntechlife.entity.user.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: skwen
 * @ClassName: YuntechFlowConfigMapper
 * @Description: dao
 * @Date: 2020-03-11
 */
@Mapper
public interface YuntechFlowConfigMapper {
    YuntechFlowConfig getById(YuntechFlowConfig yuntechFlowConfig);
}