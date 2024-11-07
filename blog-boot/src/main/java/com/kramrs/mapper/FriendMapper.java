package com.kramrs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kramrs.entity.Friend;
import com.kramrs.model.vo.query.FriendQuery;
import com.kramrs.model.vo.response.FriendBackResp;
import com.kramrs.model.vo.response.FriendResp;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: kramrs
 * @Description: 友链 Mapper
 */
@Mapper
public interface FriendMapper extends BaseMapper<Friend> {

    /**
     * 查看友链列表
     *
     * @return 友链列表
     */
    List<FriendResp> selectFriendVOList();

    /**
     * 查看友链后台列表
     *
     * @param friendQuery 友链查询条件
     * @return 友链后台列表
     */
    List<FriendBackResp> selectFriendBackVOList(@Param("param") FriendQuery friendQuery);

}
