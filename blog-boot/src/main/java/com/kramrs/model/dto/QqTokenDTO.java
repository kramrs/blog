package com.kramrs.model.dto;

import lombok.Data;

/**
 * @Author: kramrs
 * @Description: QQ token信息
 */
@Data
public class QqTokenDTO {

    /**
     * openid
     */
    private String openid;

    /**
     * 客户端id
     */
    private String client_id;
}
