package com.kramrs.model.vo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: kramrs
 * @Description: 分类查询条件
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "分类查询条件")
public class CategoryQuery extends PageQuery {

    /**
     * 搜索内容
     */
    @ApiModelProperty(value = "搜索内容")
    private String keyword;

}
