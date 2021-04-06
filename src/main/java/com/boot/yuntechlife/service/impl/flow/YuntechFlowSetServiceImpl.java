package com.boot.yuntechlife.service.impl.flow;

import com.boot.yuntechlife.dao.flow.YuntechFlowMapper;
import com.boot.yuntechlife.dao.flow.YuntechFlowSetMapper;
import com.boot.yuntechlife.entity.flow.YuntechFlow;
import com.boot.yuntechlife.entity.flow.YuntechFlowSet;
import com.boot.yuntechlife.entity.user.User;
import com.boot.yuntechlife.service.flow.YuntechFlowService;
import com.boot.yuntechlife.service.flow.YuntechFlowSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: skwen
 * @ClassName: YuntechFlowSetServiceImpl
 * @Description: Service
 * @Date: 2020-03-10
 */
@Component
public class YuntechFlowSetServiceImpl implements YuntechFlowSetService {
    @Autowired
    private YuntechFlowSetMapper yuntechFlowSetMapper;

    @Override
    public int insertOne(YuntechFlowSet yuntechFlowSet) {
        return yuntechFlowSetMapper.insertOne(yuntechFlowSet);
    }

    @Override
    public List<YuntechFlowSet> getList(User user) {
        return yuntechFlowSetMapper.getList(user);
    }

    @Override
    public int deleteOne(YuntechFlowSet yuntechFlowSet) {
        return yuntechFlowSetMapper.deleteOne(yuntechFlowSet);
    }

    @Override
    public int getCount(YuntechFlowSet yuntechFlowSet) {
        return yuntechFlowSetMapper.getCount(yuntechFlowSet);
    }

    @Override
    public int updateOne(YuntechFlowSet yuntechFlowSet) {
        return yuntechFlowSetMapper.updateOne(yuntechFlowSet);
    }

    @Override
    public YuntechFlowSet getById(YuntechFlowSet yuntechFlowSet) {
        return yuntechFlowSetMapper.getById(yuntechFlowSet);
    }


}
