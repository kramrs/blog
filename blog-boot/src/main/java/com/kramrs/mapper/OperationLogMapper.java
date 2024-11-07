package com.kramrs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kramrs.entity.OperationLog;
import com.kramrs.model.vo.query.LogQuery;
import com.kramrs.model.vo.response.OperationLogResp;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: kramrs
 * @Description: 操作日志 Mapper
 */
@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLog> {

    /**
     * 查询操作日志
     *
     * @param logQuery 条件
     * @return 操作日志列表
     */
    List<OperationLogResp> selectOperationLogVOList(@Param("param") LogQuery logQuery);

}
