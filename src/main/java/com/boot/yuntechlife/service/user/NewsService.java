package com.boot.yuntechlife.service.user;

import com.boot.yuntechlife.entity.user.News;
import com.boot.yuntechlife.entity.user.User;

import java.util.List;

public interface NewsService {
    int insertOne(News news);

    int getCount(News news);

    List<News> getList(User user);

    int deleteById(News news);

    int updateById(News news);

    int updateByUserId(News news);

    int allRead(News news);
}
