package com.kramrs.model.vo.query;

import com.kramrs.constant.PageConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: kramrs
 * @Description: 分页查询条件
 */
@Data
@ApiModel(description = "分页查询条件")
public class PageQuery {

    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页", required = true)
    private Integer current;

    /**
     * 条数
     */
    @ApiModelProperty(value = "条数", required = true)
    private Integer size;

    public Integer getCurrent() {
        return current == null ? (PageConstant.DEFAULT_CURRENT - 1) * getSize() : (current - 1) * getSize();
    }

    public Integer getSize() {
        return size == null ? PageConstant.MY_SIZE : size;
    }

}
