package com.kramrs.model.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: kramrs
 * @Description: 验证码返回Response
 */
@Data
@ApiModel(description = "验证码返回Response")
public class CaptchaResp {

    /**
     * 验证码UUID
     */
    @ApiModelProperty(value = "验证码UUID")
    private String captchaUUID;

    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码")
    private String code;
}
