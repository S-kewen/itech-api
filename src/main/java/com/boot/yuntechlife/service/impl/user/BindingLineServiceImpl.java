package com.boot.yuntechlife.service.impl.user;

import com.boot.yuntechlife.dao.user.BindingLineMapper;
import com.boot.yuntechlife.dao.user.CardMapper;
import com.boot.yuntechlife.entity.user.BindingLine;
import com.boot.yuntechlife.entity.user.Card;
import com.boot.yuntechlife.service.user.BindingLineService;
import com.boot.yuntechlife.service.user.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: skwen
 * @ClassName: BindingLineServiceImpl
 * @Description: Service
 * @Date: 2020-03-24
 */
@Component
public class BindingLineServiceImpl implements BindingLineService {
    @Autowired
    private BindingLineMapper bindingLineMapper;

    @Override
    public int insertOne(BindingLine bindingLine) {
        return bindingLineMapper.insertOne(bindingLine);
    }
    @Override
    public int getCount(BindingLine bindingLine) {
        return bindingLineMapper.getCount(bindingLine);
    }
    @Override
    public int deleteOne(BindingLine bindingLine) {
        return bindingLineMapper.deleteOne(bindingLine);
    }

    @Override
    public BindingLine getInfo(BindingLine bindingLine) {
        return bindingLineMapper.getInfo(bindingLine);
    }
    @Override
    public BindingLine getInfoByLine(BindingLine bindingLine) {
        return bindingLineMapper.getInfoByLine(bindingLine);
    }
}
