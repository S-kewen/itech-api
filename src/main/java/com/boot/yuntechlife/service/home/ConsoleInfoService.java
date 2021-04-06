package com.boot.yuntechlife.service.home;

import com.boot.yuntechlife.entity.home.Console;
import com.boot.yuntechlife.entity.user.User;

public interface ConsoleInfoService {
    Console getByUserId(User user);
}
