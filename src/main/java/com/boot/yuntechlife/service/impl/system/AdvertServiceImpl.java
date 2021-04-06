package com.boot.yuntechlife.service.impl.system;

import com.boot.yuntechlife.dao.system.AdvertMapper;
import com.boot.yuntechlife.dao.system.NoticeMapper;
import com.boot.yuntechlife.entity.system.Advert;
import com.boot.yuntechlife.entity.system.Notice;
import com.boot.yuntechlife.service.system.AdvertService;
import com.boot.yuntechlife.service.system.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: skwen
 * @ClassName: NoticeServiceImpl
 * @Description: Service
 * @Date: 2020-03-17
 */
@Component
public class AdvertServiceImpl implements AdvertService {
    @Autowired
    private AdvertMapper advertMapper;

    @Override
    public List<Advert> getList(Advert advert) {
        return advertMapper.getList(advert);
    }
}

