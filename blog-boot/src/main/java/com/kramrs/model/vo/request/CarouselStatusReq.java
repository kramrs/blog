package com.kramrs.model.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: kramrs
 * @Description: 轮播图状态Request
 */
@Data
@ApiModel(description = "轮播图状态Request")
public class CarouselStatusReq {

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    @ApiModelProperty(value = "id", required = true)
    private Integer id;

    /**
     * 是否显示 (0否 1是)
     */
    @NotNull(message = "显示状态不能为空")
    @ApiModelProperty(value = "是否显示 (0否 1是)", required = true)
    private Integer status;
}
