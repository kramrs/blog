package com.kramrs.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: kramrs
 * @Description: 时区枚举
 */
@Getter
@AllArgsConstructor
public enum ZoneEnum {

    /**
     * 上海
     */
    SHANGHAI("Asia/Shanghai", "中国上海");

    /**
     * 时区
     */
    private final String zone;

    /**
     * 描述
     */
    private final String description;

}
