package com.boot.yuntechlife.service.impl.express;

import com.boot.yuntechlife.dao.express.ExpressTakerMapper;
import com.boot.yuntechlife.dao.user.NewsMapper;
import com.boot.yuntechlife.entity.express.ExpressTaker;
import com.boot.yuntechlife.entity.user.News;
import com.boot.yuntechlife.entity.user.User;
import com.boot.yuntechlife.service.express.ExpressTakerService;
import com.boot.yuntechlife.service.user.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: skwen
 * @ClassName: ExpressTakerServiceImpl
 * @Description: service
 * @Date: 2020-03-15
 */
@Component
public class ExpressTakerServiceImpl implements ExpressTakerService {
    @Autowired
    private ExpressTakerMapper expressTakerMapper;

    @Override
    public int insertOne(ExpressTaker expressTaker) {
        return expressTakerMapper.insertOne(expressTaker);
    }

    @Override
    public int getCount(ExpressTaker expressTaker) {
        return expressTakerMapper.getCount(expressTaker);
    }

    @Override
    public List<ExpressTaker> getList(User user) {
        return expressTakerMapper.getList(user);
    }

    @Override
    public int deleteOne(ExpressTaker expressTaker) {
        return expressTakerMapper.deleteOne(expressTaker);
    }

    @Override
    public int updateOne(ExpressTaker expressTaker) {
        return expressTakerMapper.updateOne(expressTaker);
    }

    @Override
    public ExpressTaker getById(ExpressTaker expressTaker) {
        return expressTakerMapper.getById(expressTaker);
    }

    @Override
    public List<ExpressTaker> getListByMarket(User user) {
        return expressTakerMapper.getListByMarket(user);
    }

    @Override
    public ExpressTaker getByReceive(ExpressTaker expressTaker) {
        return expressTakerMapper.getByReceive(expressTaker);
    }

}
