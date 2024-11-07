package com.kramrs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kramrs.entity.Talk;
import com.kramrs.model.vo.query.PageQuery;
import com.kramrs.model.vo.query.TalkQuery;
import com.kramrs.model.vo.response.TalkBackInfoResp;
import com.kramrs.model.vo.response.TalkBackResp;
import com.kramrs.model.vo.response.TalkResp;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: kramrs
 * @Description: 说说 Mapper
 */
@Mapper
public interface TalkMapper extends BaseMapper<Talk> {

    /**
     * 查询后台说说列表
     *
     * @param talkQuery 说说查询条件
     * @return 后台说说列表
     */
    List<TalkBackResp> selectBackTalkList(@Param("param") TalkQuery talkQuery);

    /**
     * 根据id查询后台说说
     *
     * @param talkId 说说id
     * @return 后台说说
     */
    TalkBackInfoResp selectTalkBackById(@Param("talkId") Integer talkId);

    /**
     * 根据id查询说说
     *
     * @param talkId 说说id
     * @return 说说
     */
    TalkResp selectTalkById(@Param("talkId") Integer talkId);

    /**
     * 查询说说列表
     *
     * @param pageQuery 分页查询条件
     * @return 说说列表
     */
    List<TalkResp> selectTalkList(@Param("param") PageQuery pageQuery);
}
