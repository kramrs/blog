package com.kramrs.model.dto;

import lombok.Data;

/**
 * @Author: kramrs
 * @Description: QQ用户信息
 */
@Data
public class QqUserInfoDTO {

    /**
     * 用户开放id
     */
    private String openId;

    /**
     * QQ头像
     */
    private String figureurl_qq_1;

    /**
     * 昵称
     */
    private String nickname;
}
