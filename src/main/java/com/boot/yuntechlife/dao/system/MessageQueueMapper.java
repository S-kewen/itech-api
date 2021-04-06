package com.boot.yuntechlife.dao.system;

import com.boot.yuntechlife.entity.system.Advert;
import com.boot.yuntechlife.entity.system.MessageQueue;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: skwen
 * @ClassName:MessageQueueMapper
 * @Description: dao
 * @Date: 2020-03-17
 */
@Mapper
public interface MessageQueueMapper {
    List<MessageQueue> getList(MessageQueue messageQueue);

    int updateOne(MessageQueue messageQueue);
}