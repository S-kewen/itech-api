package com.boot.yuntechlife.dao.system;

import com.boot.yuntechlife.entity.system.Feedback;
import com.boot.yuntechlife.entity.system.MessageBoard;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author: skwen
 * @ClassName:MessageBoardMapper
 * @Description: dao
 * @Date: 2020-03-15
 */
@Mapper
public interface MessageBoardMapper {
    int insertOne(MessageBoard messageBoard);

    int getCount(MessageBoard messageBoard);

    List<Map<String, Object>> getList(MessageBoard messageBoard);
}