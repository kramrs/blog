package com.kramrs.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: kramrs
 * @Description: 任务状态枚举
 */
@Getter
@AllArgsConstructor
public enum TaskStatusEnum {

    /**
     * 运行
     */
    RUNNING(0),

    /**
     * 暂停
     */
    PAUSE(1);

    /**
     * 状态
     */
    private final Integer status;
}

