package com.boot.yuntechlife.service.impl.system;

import com.boot.yuntechlife.dao.system.MessageBoardMapper;
import com.boot.yuntechlife.dao.system.NoticeMapper;
import com.boot.yuntechlife.entity.system.MessageBoard;
import com.boot.yuntechlife.entity.system.Notice;
import com.boot.yuntechlife.service.system.MessageBoardService;
import com.boot.yuntechlife.service.system.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author: skwen
 * @ClassName: NoticeServiceImpl
 * @Description: Service
 * @Date: 2020-03-17
 */
@Component
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public List<Notice> getList(Notice notice) {
        return noticeMapper.getList(notice);
    }
}

