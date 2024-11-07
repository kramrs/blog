package com.kramrs.model.vo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: kramrs
 * @Description: 轮播图查询条件
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "轮播图查询条件")
public class CarouselQuery extends PageQuery {

    /**
     * 是否显示 (0否 1是)
     */
    @ApiModelProperty(value = "是否显示 (0否 1是)")
    private Integer status;
}
