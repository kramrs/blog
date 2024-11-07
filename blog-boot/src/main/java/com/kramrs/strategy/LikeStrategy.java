package com.kramrs.strategy;

/**
 * @Author: kramrs
 * @Description: 点赞策略
 */
public interface LikeStrategy {

    /**
     * 点赞
     *
     * @param typeId 类型id
     */
    void like(Integer typeId);
}
