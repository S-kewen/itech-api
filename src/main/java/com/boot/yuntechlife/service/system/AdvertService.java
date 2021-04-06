package com.boot.yuntechlife.service.system;

import com.boot.yuntechlife.entity.system.Advert;
import com.boot.yuntechlife.entity.system.Notice;

import java.util.List;

public interface AdvertService {
    List<Advert> getList(Advert advert);
}
