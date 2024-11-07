package com.kramrs.model.vo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: kramrs
 * @Description: 标签查询条件
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "标签查询条件")
public class TagQuery extends PageQuery {

    /**
     * 搜索内容
     */
    @ApiModelProperty(value = "搜索内容")
    private String keyword;

}
