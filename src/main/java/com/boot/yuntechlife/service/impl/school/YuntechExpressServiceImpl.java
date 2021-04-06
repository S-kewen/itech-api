package com.boot.yuntechlife.service.impl.school;

import com.boot.yuntechlife.dao.school.YuntechExpressMapper;
import com.boot.yuntechlife.entity.school.YuntechExpress;
import com.boot.yuntechlife.entity.user.User;
import com.boot.yuntechlife.service.school.YuntechExpressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: skwen
 * @ClassName: ExpressIntegralServiceImpl
 * @Description: service
 * @Date: 2020-03-15
 */
@Component
public class YuntechExpressServiceImpl implements YuntechExpressService {
    @Autowired
    private YuntechExpressMapper yuntechExpressMapper;

    @Override
    public int insertOne(YuntechExpress yuntechExpress) {
        return yuntechExpressMapper.insertOne(yuntechExpress);
    }

    @Override
    public int getCount(YuntechExpress yuntechExpress) {
        return yuntechExpressMapper.getCount(yuntechExpress);
    }

    @Override
    public int updateOne(YuntechExpress yuntechExpress) {
        return yuntechExpressMapper.updateOne(yuntechExpress);
    }

    @Override
    public List<YuntechExpress> getList(User user) {
        return yuntechExpressMapper.getList(user);
    }

    @Override
    public List<YuntechExpress> getListByTask(User user) {
        return yuntechExpressMapper.getListByTask(user);
    }

}
