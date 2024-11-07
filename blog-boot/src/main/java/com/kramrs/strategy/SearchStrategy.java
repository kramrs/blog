package com.kramrs.strategy;

import com.kramrs.model.vo.response.ArticleSearchResp;

import java.util.List;

/**
 * @Author: kramrs
 * @Description: 文章搜索策略
 */
public interface SearchStrategy {

    /**
     * 搜索文章
     *
     * @param keyword 关键字
     * @return {@link List< ArticleSearchResp >} 文章列表
     */
    List<ArticleSearchResp> searchArticle(String keyword);
}
