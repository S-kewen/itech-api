package com.boot.yuntechlife.service.system;

import com.boot.yuntechlife.entity.system.Feedback;
import com.boot.yuntechlife.entity.system.MessageBoard;

import java.util.List;
import java.util.Map;

public interface MessageBoardService {
    int insertOne(MessageBoard messageBoard);

    int getCount(MessageBoard messageBoard);

    List<Map<String, Object>> getList(MessageBoard messageBoard);
}
