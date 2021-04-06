package com.boot.yuntechlife.dao.user;

import com.boot.yuntechlife.entity.user.LoginRecord;
import com.boot.yuntechlife.entity.user.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author: skwen
 * @ClassName:LoginRecordMapper
 * @Description: 登錄記錄dao
 * @Date: 2020-03-08
 */
@Mapper
public interface LoginRecordMapper {
    LoginRecord getByIp(LoginRecord loginRecord);

    int insertOne(LoginRecord loginRecord);

    List<LoginRecord> getList(User user);

    int deleteOne(LoginRecord loginRecord);

    int getCount(LoginRecord loginRecord);

    List<Map<String, Object>> getCountByWeek(LoginRecord loginRecord);
}