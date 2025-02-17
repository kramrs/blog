package com.kramrs.model.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @Author: kramrs
 * @Description: 文章浏览量排行Response
 */
@Data
@Builder
@ApiModel(description = "文章浏览量排行Response")
public class ArticleRankResp {

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String articleTitle;

    /**
     * 浏览量
     */
    @ApiModelProperty(value = "浏览量")
    private Integer viewCount;

}
