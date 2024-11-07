package com.kramrs.strategy;

import com.kramrs.model.vo.request.CodeReq;

/**
 * @Author: kramrs
 * @Description: 第三方登录策略
 */
public interface SocialLoginStrategy {

    /**
     * 登录
     *
     * @param data 第三方code
     * @return {@link String} Token
     */
    String login(CodeReq data);
}
