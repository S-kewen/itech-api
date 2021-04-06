package com.boot.yuntechlife.service.impl.user;

import com.boot.yuntechlife.dao.user.NewsMapper;
import com.boot.yuntechlife.dao.user.UserMapper;
import com.boot.yuntechlife.entity.user.News;
import com.boot.yuntechlife.entity.user.User;
import com.boot.yuntechlife.service.user.NewsService;
import com.boot.yuntechlife.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: skwen
 * @ClassName: UserServiceImpl
 * @Description: userService
 * @Date: 2020-03-08
 */
@Component
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsMapper newsMapper;

    @Override
    public int insertOne(News news) {
        return newsMapper.insertOne(news);
    }

    @Override
    public int getCount(News news) {
        return newsMapper.getCount(news);
    }

    @Override
    public List<News> getList(User user) {
        return newsMapper.getList(user);
    }

    @Override
    public int deleteById(News news) {
        return newsMapper.deleteById(news);
    }

    @Override
    public int updateById(News news) {
        return newsMapper.updateById(news);
    }

    @Override
    public int updateByUserId(News news) {
        return newsMapper.updateByUserId(news);
    }

    @Override
    public int allRead(News news) {
        return newsMapper.allRead(news);
    }


}
