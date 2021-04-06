package com.boot.yuntechlife.service.impl.user;

import com.boot.yuntechlife.dao.user.CardMapper;
import com.boot.yuntechlife.dao.user.RechargeRecordMapper;
import com.boot.yuntechlife.entity.user.Card;
import com.boot.yuntechlife.entity.user.RechargeRecord;
import com.boot.yuntechlife.service.user.CardService;
import com.boot.yuntechlife.service.user.RechargeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: skwen
 * @ClassName: RechargeRecordServiceImpl
 * @Description: Service
 * @Date: 2020-03-14
 */
@Component
public class RechargeRecordServiceImpl implements RechargeRecordService {
    @Autowired
    private RechargeRecordMapper rechargeRecordMapper;

    @Override
    public int insertOne(RechargeRecord rechargeRecord) {
        return rechargeRecordMapper.insertOne(rechargeRecord);
    }

}
