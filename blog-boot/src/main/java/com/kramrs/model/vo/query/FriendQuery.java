package com.kramrs.model.vo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: kramrs
 * @Description: 友链查询条件
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "友链查询条件")
public class FriendQuery extends PageQuery {

    /**
     * 搜索内容
     */
    @ApiModelProperty(value = "搜索内容")
    private String keyword;

}