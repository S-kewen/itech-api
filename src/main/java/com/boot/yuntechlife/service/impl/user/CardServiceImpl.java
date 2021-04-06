package com.boot.yuntechlife.service.impl.user;

import com.boot.yuntechlife.dao.user.CardMapper;
import com.boot.yuntechlife.dao.user.LoginRecordMapper;
import com.boot.yuntechlife.entity.user.Card;
import com.boot.yuntechlife.entity.user.LoginRecord;
import com.boot.yuntechlife.entity.user.User;
import com.boot.yuntechlife.service.user.CardService;
import com.boot.yuntechlife.service.user.LoginRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author: skwen
 * @ClassName: CardServiceImpl
 * @Description: Service
 * @Date: 2020-03-08
 */
@Component
public class CardServiceImpl implements CardService {
    @Autowired
    private CardMapper cardMapper;

    @Override
    public Card getByCdkey(Card card) {
        return cardMapper.getByCdkey(card);
    }

    @Override
    public int insertOne(Card card) {
        return cardMapper.insertOne(card);
    }

    @Override
    public int updateById(Card card) {
        return cardMapper.updateById(card);
    }

}
