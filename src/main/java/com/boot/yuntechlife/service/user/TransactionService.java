package com.boot.yuntechlife.service.user;

import com.boot.yuntechlife.entity.user.LoginRecord;
import com.boot.yuntechlife.entity.user.Transaction;
import com.boot.yuntechlife.entity.user.User;

import java.util.List;

public interface TransactionService {
    int insertOne(Transaction transaction);

    List<Transaction> getList(User user);

    int deleteOne(Transaction transaction);
}
