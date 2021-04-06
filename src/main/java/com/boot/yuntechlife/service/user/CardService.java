package com.boot.yuntechlife.service.user;

import com.boot.yuntechlife.entity.user.Card;
import com.boot.yuntechlife.entity.user.LoginRecord;
import com.boot.yuntechlife.entity.user.User;

import java.util.List;
import java.util.Map;

public interface CardService {
    Card getByCdkey(Card card);

    int insertOne(Card card);

    int updateById(Card card);
}
