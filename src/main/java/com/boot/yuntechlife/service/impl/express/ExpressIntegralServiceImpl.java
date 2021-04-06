package com.boot.yuntechlife.service.impl.express;

import com.boot.yuntechlife.dao.express.ExpressIntegralMapper;
import com.boot.yuntechlife.dao.express.ExpressTakerMapper;
import com.boot.yuntechlife.entity.express.ExpressIntegral;
import com.boot.yuntechlife.entity.express.ExpressTaker;
import com.boot.yuntechlife.entity.user.User;
import com.boot.yuntechlife.service.express.ExpressIntegralService;
import com.boot.yuntechlife.service.express.ExpressTakerService;
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
public class ExpressIntegralServiceImpl implements ExpressIntegralService {
    @Autowired
    private ExpressIntegralMapper expressIntegralMapper;

    @Override
    public ExpressIntegral getByUserId(ExpressIntegral expressIntegral) {
        return expressIntegralMapper.getByUserId(expressIntegral);
    }

}
