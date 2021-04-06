package com.boot.yuntechlife.service.user;

import com.boot.yuntechlife.entity.user.BindingLine;

public interface BindingLineService {
    int insertOne(BindingLine bindingLine);
    int getCount(BindingLine bindingLine);
    int deleteOne(BindingLine bindingLine);
    BindingLine getInfo(BindingLine bindingLine);
    BindingLine getInfoByLine(BindingLine bindingLine);
}
