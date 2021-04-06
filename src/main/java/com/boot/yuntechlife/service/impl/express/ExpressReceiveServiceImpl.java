package com.boot.yuntechlife.service.impl.express;

import com.boot.yuntechlife.dao.express.ExpressReceiveMapper;
import com.boot.yuntechlife.dao.express.ExpressTakerMapper;
import com.boot.yuntechlife.entity.express.ExpressReceive;
import com.boot.yuntechlife.entity.express.ExpressTaker;
import com.boot.yuntechlife.entity.user.User;
import com.boot.yuntechlife.service.express.ExpressReceiveService;
import com.boot.yuntechlife.service.express.ExpressTakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author: skwen
 * @ClassName: ExpressTakerServiceImpl
 * @Description: service
 * @Date: 2020-03-15
 */
@Component
public class ExpressReceiveServiceImpl implements ExpressReceiveService {
    @Autowired
    private ExpressReceiveMapper expressReceiveMapper;

    @Override
    public int insertOne(ExpressReceive expressReceive) {
        return expressReceiveMapper.insertOne(expressReceive);
    }

    @Override
    public int getCount(ExpressReceive expressReceive) {
        return expressReceiveMapper.getCount(expressReceive);
    }

    @Override
    public List<Map<String, Object>> getList(User user) {
        return expressReceiveMapper.getList(user);
    }

    @Override
    public int deleteOne(ExpressReceive expressReceive) {
        return expressReceiveMapper.deleteOne(expressReceive);
    }

    @Override
    public int updateOne(ExpressReceive expressReceive) {
        return expressReceiveMapper.updateOne(expressReceive);
    }

    @Override
    public ExpressReceive getById(ExpressReceive expressReceive) {
        return expressReceiveMapper.getById(expressReceive);
    }

    @Override
    public int getCountByPending(ExpressReceive expressReceive) {
        return expressReceiveMapper.getCountByPending(expressReceive);
    }

    @Override
    public ExpressReceive getByExpressTakerId(ExpressReceive expressReceive) {
        return expressReceiveMapper.getByExpressTakerId(expressReceive);
    }

    @Override
    public ExpressReceive getByIssuance(ExpressReceive expressReceive) {
        return expressReceiveMapper.getByIssuance(expressReceive);
    }

    @Override
    public int getCountByTotal(ExpressReceive expressReceive) {
        return expressReceiveMapper.getCountByTotal(expressReceive);
    }

}
