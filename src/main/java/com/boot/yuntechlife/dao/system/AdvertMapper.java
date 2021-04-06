package com.boot.yuntechlife.dao.system;

import com.boot.yuntechlife.entity.system.Advert;
import com.boot.yuntechlife.entity.system.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: skwen
 * @ClassName:AdvertMapper
 * @Description: dao
 * @Date: 2020-03-17
 */
@Mapper
public interface AdvertMapper {
    List<Advert> getList(Advert advert);
}