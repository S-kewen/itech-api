package com.boot.yuntechlife.dao.user;

import com.boot.yuntechlife.entity.user.News;
import com.boot.yuntechlife.entity.user.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: skwen
 * @ClassName:NewsMapper
 * @Description: 消息dao
 * @Date: 2020-03-08
 */
@Mapper
public interface NewsMapper {
    int insertOne(News news);

    int getCount(News news);

    List<News> getList(User user);

    int deleteById(News news);

    int updateById(News news);

    int updateByUserId(News news);

    int allRead(News news);
}