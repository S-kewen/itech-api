package com.boot.yuntechlife.dao.user;

import com.alibaba.druid.sql.visitor.functions.Bin;
import com.boot.yuntechlife.entity.user.BindingLine;
import com.boot.yuntechlife.entity.user.Card;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: skwen
 * @ClassName:BindingLineMapper
 * @Description: dao
 * @Date: 2020-03-25
 */
@Mapper
public interface BindingLineMapper {
    int insertOne(BindingLine bindingLine);
    int getCount(BindingLine bindingLine);
    int deleteOne(BindingLine bindingLine);
    BindingLine getInfo(BindingLine bindingLine);
    BindingLine getInfoByLine(BindingLine bindingLine);
}