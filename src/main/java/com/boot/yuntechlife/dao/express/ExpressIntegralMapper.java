package com.boot.yuntechlife.dao.express;

import com.boot.yuntechlife.entity.express.ExpressIntegral;
import com.boot.yuntechlife.entity.express.ExpressReceive;
import com.boot.yuntechlife.entity.user.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author: skwen
 * @ClassName: ExpressIntegralMapper
 * @Description: dao
 * @Date: 2020-03-16
 */
@Mapper
public interface ExpressIntegralMapper {
    ExpressIntegral getByUserId(ExpressIntegral expressIntegral);
}