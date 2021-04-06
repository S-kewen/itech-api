package com.boot.yuntechlife.service.impl.flow;

import com.boot.yuntechlife.dao.flow.YuntechFlowSetMapper;
import com.boot.yuntechlife.dao.flow.YuntechFlowWarnMapper;
import com.boot.yuntechlife.entity.flow.YuntechFlowSet;
import com.boot.yuntechlife.entity.flow.YuntechFlowWarn;
import com.boot.yuntechlife.entity.user.User;
import com.boot.yuntechlife.service.flow.YuntechFlowSetService;
import com.boot.yuntechlife.service.flow.YuntechFlowWarnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: skwen
 * @ClassName: YuntechFlowWarnServiceImpl
 * @Description: Service
 * @Date: 2020-03-10
 */
@Component
public class YuntechFlowWarnServiceImpl implements YuntechFlowWarnService {
    @Autowired
    private YuntechFlowWarnMapper yuntechFlowWarnMapper;

    @Override
    public int insertOne(YuntechFlowWarn yuntechFlowWarn) {
        return yuntechFlowWarnMapper.insertOne(yuntechFlowWarn);
    }

    @Override
    public List<YuntechFlowWarn> getList(User user) {
        return yuntechFlowWarnMapper.getList(user);
    }

    @Override
    public int deleteOne(YuntechFlowWarn yuntechFlowWarn) {
        return yuntechFlowWarnMapper.deleteOne(yuntechFlowWarn);
    }

    @Override
    public int updateOne(YuntechFlowWarn yuntechFlowWarn) {
        return yuntechFlowWarnMapper.updateOne(yuntechFlowWarn);
    }

    @Override
    public List<YuntechFlowWarn> disposeWarn(YuntechFlowWarn yuntechFlowWarn) {
        return yuntechFlowWarnMapper.disposeWarn(yuntechFlowWarn);
    }


}
