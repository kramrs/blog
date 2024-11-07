package com.kramrs.model.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @Author: kramrs
 * @Description: 登录信息Request
 */
@Data
@ApiModel(description = "登录信息Request")
public class LoginReq {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    /**
     * 用户密码
     */
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, message = "密码不能少于6位")
    @ApiModelProperty(value = "用户密码", required = true)
    private String password;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码", required = true)
    private String code;

    /**
     * 验证码UUID
     */
    @NotBlank(message = "验证码UUID")
    @ApiModelProperty(value = "验证码UUID", required = true)
    private String captchaUUID;

    /**
     * 平台类型
     */
    @NotBlank(message = "平台类型不能为空")
    @ApiModelProperty(value = "平台类型", required = true)
    private String type;

}
