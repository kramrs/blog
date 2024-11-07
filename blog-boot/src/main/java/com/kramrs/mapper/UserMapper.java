package com.kramrs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kramrs.entity.User;
import com.kramrs.model.vo.query.UserQuery;
import com.kramrs.model.vo.response.UserBackResp;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: kramrs
 * @Description: 用户 Mapper
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查询用户后台数量
     *
     * @param userQuery 用户查询条件
     * @return 用户数量
     */
    Long selectUserCount(@Param("param") UserQuery userQuery);

    /**
     * 查询后台用户列表
     *
     * @param userQuery 用户查询条件
     * @return 用户后台列表
     */
    List<UserBackResp> selectUserList(@Param("param") UserQuery userQuery);
}
