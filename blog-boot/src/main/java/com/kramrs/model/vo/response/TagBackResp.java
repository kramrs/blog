package com.kramrs.model.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @Author: kramrs
 * @Description: 标签后台Response
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "标签后台Response")
public class TagBackResp extends TagResp {

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

}
