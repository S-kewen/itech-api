package com.boot.yuntechlife.service.impl.express;

import com.boot.yuntechlife.dao.express.ExpressIntegralMapper;
import com.boot.yuntechlife.dao.express.ExpressScoreMapper;
import com.boot.yuntechlife.entity.express.ExpressIntegral;
import com.boot.yuntechlife.entity.express.ExpressScore;
import com.boot.yuntechlife.service.express.ExpressIntegralService;
import com.boot.yuntechlife.service.express.ExpressScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: skwen
 * @ClassName: ExpressIntegralServiceImpl
 * @Description: service
 * @Date: 2020-03-15
 */
@Component
public class ExpressScoreServiceImpl implements ExpressScoreService {
    @Autowired
    private ExpressScoreMapper expressScoreMapper;

    @Override
    public int insertOne(ExpressScore expressScore) {
        return expressScoreMapper.insertOne(expressScore);
    }

}
