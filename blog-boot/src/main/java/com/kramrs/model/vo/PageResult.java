package com.kramrs.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: kramrs
 * @Description: 分页返回类
 */
@Data
@AllArgsConstructor
@ApiModel(description = "分页返回类")
public class PageResult<T> {

    /**
     * 分页结果
     */
    @ApiModelProperty(value = "分页结果")
    private List<T> recordList;

    /**
     * 总数
     */
    @ApiModelProperty(value = "总数", dataType = "long")
    private Long count;

    public PageResult() {
        this.recordList = new ArrayList<>();
        this.count = 0L;
    }
}
