package com.boot.yuntechlife.dao.user;

import com.boot.yuntechlife.entity.user.LoginRecord;
import com.boot.yuntechlife.entity.user.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author: skwen
 * @ClassName:UserMapper
 * @Description: 用戶相關dao
 * @Date: 2020-03-08
 */
@Mapper
public interface UserMapper {
    User getById(User user);

    User getByUsername(User user);

    User userLogin(User user);

    int updateById(User user);

    int insertOne(User user);

    int getCount(User user);

    int updateByUsername(User user);

    List<Map<String, Object>> getCountByWeek(User user);
}