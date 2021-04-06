package com.boot.yuntechlife.dao.user;

import com.boot.yuntechlife.entity.user.Card;
import com.boot.yuntechlife.entity.user.LoginRecord;
import com.boot.yuntechlife.entity.user.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author: skwen
 * @ClassName:CardMapper
 * @Description: dao
 * @Date: 2020-03-14
 */
@Mapper
public interface CardMapper {
    Card getByCdkey(Card card);

    int insertOne(Card card);

    int updateById(Card card);
}