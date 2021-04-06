package com.boot.yuntechlife.service.user;

import com.boot.yuntechlife.entity.user.LoginRecord;
import com.boot.yuntechlife.entity.user.User;
import com.boot.yuntechlife.entity.user.UserIndex;

import java.util.List;

public interface UserIndexService {
    UserIndex getByUserId(User user);
}
