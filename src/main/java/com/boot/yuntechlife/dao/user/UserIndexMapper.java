package com.boot.yuntechlife.dao.user;

import com.boot.yuntechlife.entity.user.LoginRecord;
import com.boot.yuntechlife.entity.user.User;
import com.boot.yuntechlife.entity.user.UserIndex;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: skwen
 * @ClassName: UserIndexMapper
 * @Description: 个人中心dao
 * @Date: 2020-03-09
 */
@Mapper
public interface UserIndexMapper {
    UserIndex getByUserId(User user);
}