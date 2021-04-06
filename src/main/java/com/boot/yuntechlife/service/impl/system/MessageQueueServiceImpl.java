package com.boot.yuntechlife.service.impl.system;

import com.boot.yuntechlife.dao.system.AdvertMapper;
import com.boot.yuntechlife.dao.system.MessageQueueMapper;
import com.boot.yuntechlife.entity.system.Advert;
import com.boot.yuntechlife.entity.system.MessageQueue;
import com.boot.yuntechlife.service.system.AdvertService;
import com.boot.yuntechlife.service.system.MessageQueueService;
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
public class MessageQueueServiceImpl implements MessageQueueService {
    @Autowired
    private MessageQueueMapper messageQueueMapper;

    @Override
    public List<MessageQueue> getList(MessageQueue messageQueue) {
        return messageQueueMapper.getList(messageQueue);
    }

    @Override
    public int updateOne(MessageQueue messageQueue) {
        return messageQueueMapper.updateOne(messageQueue);
    }
}

