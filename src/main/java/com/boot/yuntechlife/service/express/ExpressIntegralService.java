package com.boot.yuntechlife.service.express;

import com.boot.yuntechlife.entity.express.ExpressIntegral;
import com.boot.yuntechlife.entity.express.ExpressTaker;
import com.boot.yuntechlife.entity.user.User;

import java.util.List;

public interface ExpressIntegralService {
    ExpressIntegral getByUserId(ExpressIntegral expressIntegral);
}
