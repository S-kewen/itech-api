package com.boot.yuntechlife.dao.home;

import com.boot.yuntechlife.entity.home.Console;
import com.boot.yuntechlife.entity.user.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: skwen
 * @ClassName: ConsoleInfoMapper
 * @Description: 控制台屬性dao
 * @Date: 2020-03-09
 */
@Mapper
public interface ConsoleMapper {
    Console getByUserId(User user);
}