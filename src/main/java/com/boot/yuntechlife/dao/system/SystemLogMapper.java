package com.boot.yuntechlife.dao.system;

import com.boot.yuntechlife.entity.system.SystemLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: skwen
 * @ClassName: SystemLogMapper
 * @Description: dao
 * @Date: 2020-04-06
 */
@Mapper
public interface SystemLogMapper {
    int insertOne(SystemLog systemLog);
}