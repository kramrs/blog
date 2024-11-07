package com.kramrs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kramrs.entity.ChatRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: kramrs
 * @Description: 聊天记录 Mapper
 */
@Mapper
public interface ChatRecordMapper extends BaseMapper<ChatRecord> {
}
