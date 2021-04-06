package com.boot.yuntechlife.dao.user;

import com.boot.yuntechlife.entity.user.Card;
import com.boot.yuntechlife.entity.user.RechargeRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: skwen
 * @ClassName:RechargeRecordMapper
 * @Description: dao
 * @Date: 2020-03-14
 */
@Mapper
public interface RechargeRecordMapper {
    int insertOne(RechargeRecord rechargeRecord);
}