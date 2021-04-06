package com.boot.yuntechlife.service.impl.flow;

import com.boot.yuntechlife.dao.flow.YuntechFlowMapper;
import com.boot.yuntechlife.dao.user.TransactionMapper;
import com.boot.yuntechlife.entity.flow.YuntechFlow;
import com.boot.yuntechlife.entity.user.Transaction;
import com.boot.yuntechlife.entity.user.User;
import com.boot.yuntechlife.service.flow.YuntechFlowService;
import com.boot.yuntechlife.service.user.TransactionService;
import com.boot.yuntechlife.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: skwen
 * @ClassName: YuntechFlowServiceImpl
 * @Description: Service
 * @Date: 2020-03-10
 */
@Component
public class YuntechFlowServiceImpl implements YuntechFlowService {
    @Autowired
    private YuntechFlowMapper yuntechFlowMapper;

    @Override
    public int insertOne(YuntechFlow yuntechFlow) {
        return yuntechFlowMapper.insertOne(yuntechFlow);
    }

    @Override
    public List<YuntechFlow> getList(User user) {
        return yuntechFlowMapper.getList(user);
    }

    @Override
    public int deleteOne(YuntechFlow yuntechFlow) {
        return yuntechFlowMapper.deleteOne(yuntechFlow);
    }

    @Override
    public List<YuntechFlow> getDayInfoByWeek(YuntechFlow yuntechFlow) {
        return yuntechFlowMapper.getDayInfoByWeek(yuntechFlow);
    }

    @Override
    public YuntechFlow getSumByIp(YuntechFlow yuntechFlow) {
        return yuntechFlowMapper.getSumByIp(yuntechFlow);
    }

    @Override
    public int getDayNumByIp(YuntechFlow yuntechFlow) {
        return yuntechFlowMapper.getDayNumByIp(yuntechFlow);
    }

    @Override
    public List<YuntechFlow> getListByDay(User user) {
        return yuntechFlowMapper.getListByDay(user);
    }

    @Override
    public YuntechFlow getByIp(YuntechFlow yuntechFlow) {
        return yuntechFlowMapper.getByIp(yuntechFlow);
    }


}
