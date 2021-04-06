package com.boot.yuntechlife.service.impl.flow;

import com.boot.yuntechlife.dao.flow.YuntechFlowConfigMapper;
import com.boot.yuntechlife.dao.flow.YuntechFlowSetMapper;
import com.boot.yuntechlife.entity.flow.YuntechFlowConfig;
import com.boot.yuntechlife.entity.flow.YuntechFlowSet;
import com.boot.yuntechlife.entity.user.User;
import com.boot.yuntechlife.service.flow.YuntechFlowConfigService;
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
public class YuntechFlowConfigServiceImpl implements YuntechFlowConfigService {
    @Autowired
    private YuntechFlowConfigMapper yuntechFlowConfigMapper;

    @Override
    public YuntechFlowConfig getById(YuntechFlowConfig yuntechFlowConfig) {
        return yuntechFlowConfigMapper.getById(yuntechFlowConfig);
    }
}
