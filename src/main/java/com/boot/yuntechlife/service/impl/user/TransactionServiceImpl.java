package com.boot.yuntechlife.service.impl.user;

import com.boot.yuntechlife.dao.user.LoginRecordMapper;
import com.boot.yuntechlife.dao.user.TransactionMapper;
import com.boot.yuntechlife.entity.user.LoginRecord;
import com.boot.yuntechlife.entity.user.Transaction;
import com.boot.yuntechlife.entity.user.User;
import com.boot.yuntechlife.service.user.LoginRecordService;
import com.boot.yuntechlife.service.user.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: skwen
 * @ClassName: TransactionServiceImpl
 * @Description: Service
 * @Date: 2020-03-10
 */
@Component
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionMapper transactionMapper;

    @Override
    public int insertOne(Transaction transaction) {
        return transactionMapper.insertOne(transaction);
    }

    @Override
    public List<Transaction> getList(User user) {
        return transactionMapper.getList(user);
    }

    @Override
    public int deleteOne(Transaction transaction) {
        return transactionMapper.deleteOne(transaction);
    }


}
