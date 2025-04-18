package com.kramrs.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kramrs.entity.TaskLog;
import com.kramrs.mapper.TaskLogMapper;
import com.kramrs.model.vo.PageResult;
import com.kramrs.model.vo.query.TaskQuery;
import com.kramrs.model.vo.response.TaskLogResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: kramrs
 * @Description: 定时任务日志服务
 */
@Service
public class TaskLogService extends ServiceImpl<TaskLogMapper, TaskLog> {

    @Autowired
    private TaskLogMapper taskLogMapper;

    public PageResult<TaskLogResp> listTaskLog(TaskQuery taskQuery) {
        // 查询定时任务日志数量
        Long count = taskLogMapper.selectTaskLogCount(taskQuery);
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询定时任务日志列表
        List<TaskLogResp> taskLogRespList = taskLogMapper.selectTaskLogRespList(taskQuery);
        return new PageResult<>(taskLogRespList, count);
    }

    public void clearTaskLog() {
        taskLogMapper.delete(null);
    }
}
