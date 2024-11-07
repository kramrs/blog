package com.kramrs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kramrs.entity.Message;
import com.kramrs.model.vo.query.MessageQuery;
import com.kramrs.model.vo.response.MessageBackResp;
import com.kramrs.model.vo.response.MessageResp;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: kramrs
 * @Description: 留言 Mapper
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

    /**
     * 查询留言列表
     *
     * @return 留言列表
     */
    List<MessageResp> selectMessageVOList();

    /**
     * 查询后台留言列表
     *
     * @param messageQuery 留言查询条件
     * @return 后台留言列表
     */
    List<MessageBackResp> selectBackMessageList(@Param("param") MessageQuery messageQuery);
}

