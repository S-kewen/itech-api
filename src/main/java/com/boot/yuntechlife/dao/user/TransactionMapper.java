package com.boot.yuntechlife.dao.user;

import com.boot.yuntechlife.entity.user.LoginRecord;
import com.boot.yuntechlife.entity.user.Transaction;
import com.boot.yuntechlife.entity.user.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: skwen
 * @ClassName: TransactionMapper
 * @Description: dao
 * @Date: 2020-03-10
 */
@Mapper
public interface TransactionMapper {
    int insertOne(Transaction transaction);

    List<Transaction> getList(User user);

    int deleteOne(Transaction transaction);
}