package com.boot.yuntechlife.service.impl.system;

import com.boot.yuntechlife.dao.system.FeedbackMapper;
import com.boot.yuntechlife.dao.system.MessageBoardMapper;
import com.boot.yuntechlife.entity.system.Feedback;
import com.boot.yuntechlife.entity.system.MessageBoard;
import com.boot.yuntechlife.service.system.FeedbackService;
import com.boot.yuntechlife.service.system.MessageBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author: skwen
 * @ClassName: MessageBoardServiceImpl
 * @Description: Service
 * @Date: 2020-03-15
 */
@Component
public class MessageBoardServiceImpl implements MessageBoardService {
    @Autowired
    private MessageBoardMapper messageBoardMapper;

    @Override
    public int insertOne(MessageBoard messageBoard) {
        return messageBoardMapper.insertOne(messageBoard);
    }

    @Override
    public int getCount(MessageBoard messageBoard) {
        return messageBoardMapper.getCount(messageBoard);
    }

    @Override
    public List<Map<String, Object>> getList(MessageBoard messageBoard) {
        return messageBoardMapper.getList(messageBoard);
    }
}

