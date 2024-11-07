package com.kramrs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kramrs.entity.ExceptionLog;
import com.kramrs.model.vo.query.LogQuery;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: kramrs
 * @Description: 异常日志 Mapper
 */
@Mapper
public interface ExceptionLogMapper extends BaseMapper<ExceptionLog> {

    /**
     * 查询异常日志
     *
     * @param logQuery 异常日志查询条件
     * @return 异常日志列表
     */
    List<ExceptionLog> selectExceptionLogList(@Param("param") LogQuery logQuery);
}
