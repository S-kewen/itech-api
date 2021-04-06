package com.boot.yuntechlife.dao.system;

import com.boot.yuntechlife.entity.system.MessageBoard;
import com.boot.yuntechlife.entity.system.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author: skwen
 * @ClassName:NoticeMapper
 * @Description: dao
 * @Date: 2020-03-17
 */
@Mapper
public interface NoticeMapper {
    List<Notice> getList(Notice notice);
}