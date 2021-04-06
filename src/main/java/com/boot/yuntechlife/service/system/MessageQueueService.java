package com.boot.yuntechlife.service.system;

import com.boot.yuntechlife.entity.system.Advert;
import com.boot.yuntechlife.entity.system.MessageQueue;

import java.util.List;

public interface MessageQueueService {
    List<MessageQueue> getList(MessageQueue messageQueue);

    int updateOne(MessageQueue messageQueue);
}
