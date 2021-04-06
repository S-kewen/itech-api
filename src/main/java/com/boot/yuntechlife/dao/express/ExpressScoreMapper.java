package com.boot.yuntechlife.dao.express;

import com.boot.yuntechlife.entity.express.ExpressIntegral;
import com.boot.yuntechlife.entity.express.ExpressScore;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: skwen
 * @ClassName: ExpressScoreMapper
 * @Description: dao
 * @Date: 2020-03-16
 */
@Mapper
public interface ExpressScoreMapper {
    int insertOne(ExpressScore expressScore);
}