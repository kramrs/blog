package com.kramrs.mapper;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kramrs.entity.VisitLog;
import com.kramrs.model.vo.query.LogQuery;
import com.kramrs.model.vo.response.UserViewResp;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: kramrs
 * @Description: 访问日志 Mapper
 */
@Mapper
public interface VisitLogMapper extends BaseMapper<VisitLog> {

    /**
     * 查询访问日志
     *
     * @param logQuery 访问日志查询条件
     * @return 访问日志列表
     */
    List<VisitLog> selectVisitLogList(@Param("param") LogQuery logQuery);

    /**
     * 获取7天用户访问结果
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 用户访问结果
     */
    List<UserViewResp> selectUserViewList(@Param("startTime") DateTime startTime, @Param("endTime") DateTime endTime);

    /**
     * 清除一周前的访问日志
     *
     * @param endTime 结束时间
     */
    void deleteVisitLog(@Param("endTime") DateTime endTime);
}
