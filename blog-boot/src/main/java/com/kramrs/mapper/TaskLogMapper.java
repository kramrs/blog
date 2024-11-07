package com.kramrs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kramrs.entity.TaskLog;
import com.kramrs.model.vo.query.TaskQuery;
import com.kramrs.model.vo.response.TaskLogResp;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: kramrs
 * @Description: 定时任务日志 Mapper
 */
@Mapper
public interface TaskLogMapper extends BaseMapper<TaskLog> {

    /**
     * 查询定时任务日志数量
     *
     * @param taskQuery 条件
     * @return 定时任务日志数量
     */
    Long selectTaskLogCount(@Param("param") TaskQuery taskQuery);

    /**
     * 查询定时任务日志列表
     *
     * @param taskQuery 条件
     * @return 定时任务日志列表
     */
    List<TaskLogResp> selectTaskLogRespList(@Param("param") TaskQuery taskQuery);

}
