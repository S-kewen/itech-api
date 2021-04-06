package com.boot.yuntechlife.service.system;

import com.boot.yuntechlife.entity.system.MessageBoard;
import com.boot.yuntechlife.entity.system.Notice;

import java.util.List;
import java.util.Map;

public interface NoticeService {
    List<Notice> getList(Notice notice);
}
