package com.kramrs.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: kramrs
 * @Description: B站IP地址返回DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "B站IP地址返回DTO")
public class BiliIpInfoDTO {

    /**
     * 状态码
     */
    @ApiModelProperty(value = "状态码")
    private Integer code;

    /**
     * 提示信息
     */
    @ApiModelProperty(value = "提示信息")
    private String message;

    /**
     * 提示信息
     */
    @ApiModelProperty(value = "提示信息")
    private String msg;

    /**
     * 返回数据
     */
    @ApiModelProperty(value = "返回数据")
    private IpInfoData data;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel(description = "返回数据")
    public static class IpInfoData {

        /**
         * ip地址
         */
        @ApiModelProperty(value = "ip地址")
        private String addr;

        /**
         * 国家
         */
        @ApiModelProperty(value = "国家")
        private String country;

        /**
         * 省份
         */
        @ApiModelProperty(value = "省份")
        private String province;

        /**
         * 城市
         */
        @ApiModelProperty(value = "城市")
        private String city;

        /**
         * 供应商
         */
        @ApiModelProperty(value = "供应商")
        private String isp;

        /**
         * 纬度
         */
        @ApiModelProperty(value = "纬度")
        private String latitude;

        /**
         * 经度
         */
        @ApiModelProperty(value = "经度")
        private String longitude;
    }

}
