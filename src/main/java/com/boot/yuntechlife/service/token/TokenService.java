package com.boot.yuntechlife.service.token;

import com.boot.yuntechlife.entity.main.Token;

/**
 * @Author: skwen
 * ClassName: TokenService
 * @Description: TokenService-接口
 * @Date: 2020-03-08
 */
public interface TokenService {
    String createToken(Token token);

    Token parseToken(String tokenStr);


}
