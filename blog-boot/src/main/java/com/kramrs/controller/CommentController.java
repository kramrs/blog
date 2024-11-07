package com.kramrs.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.kramrs.annotation.AccessLimit;
import com.kramrs.annotation.OptLogger;
import com.kramrs.enums.LikeTypeEnum;
import com.kramrs.model.vo.PageResult;
import com.kramrs.model.vo.Result;
import com.kramrs.model.vo.query.CommentQuery;
import com.kramrs.model.vo.request.CheckReq;
import com.kramrs.model.vo.request.CommentReq;
import com.kramrs.model.vo.response.CommentBackResp;
import com.kramrs.model.vo.response.CommentResp;
import com.kramrs.model.vo.response.RecentCommentResp;
import com.kramrs.model.vo.response.ReplyResp;
import com.kramrs.service.CommentService;
import com.kramrs.strategy.context.LikeStrategyContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kramrs.constant.OptTypeConstant.UPDATE;
import static com.kramrs.constant.OptTypeConstant.DELETE;

/**
 * @Author: kramrs
 * @Description: 评论控制器
 */
@Api(tags = "评论模块")
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikeStrategyContext likeStrategyContext;

    /**
     * 查看后台评论列表
     *
     * @param commentQuery 评论查询条件
     * @return {@link Result<  CommentBackResp  >} 后台评论
     */
    @ApiOperation(value = "查看后台评论")
    @SaCheckPermission("news:comment:list")
    @GetMapping("/admin/comment/list")
    public Result<PageResult<CommentBackResp>> listCommentBackVO(CommentQuery commentQuery) {
        return Result.success(commentService.listCommentBackVO(commentQuery));
    }

    /**
     * 添加评论
     *
     * @param comment 评论信息
     * @return {@link Result<>}
     */
    @SaCheckLogin
    @ApiOperation(value = "添加评论")
    @SaCheckPermission("news:comment:add")
    @PostMapping("/comment/add")
    public Result<?> addComment(@Validated @RequestBody CommentReq comment) {
        commentService.addComment(comment);
        return Result.success();
    }

    /**
     * 删除评论
     *
     * @param commentIdList 评论id
     * @return {@link Result<>}
     */
    @OptLogger(value = DELETE)
    @ApiOperation(value = "删除评论")
    @SaCheckPermission("news:comment:delete")
    @DeleteMapping("/admin/comment/delete")
    public Result<?> deleteComment(@RequestBody List<Integer> commentIdList) {
        commentService.removeByIds(commentIdList);
        return Result.success();
    }

    /**
     * 审核评论
     *
     * @param check 审核信息
     * @return {@link Result<>}
     */
    @OptLogger(value = UPDATE)
    @ApiOperation(value = "审核评论")
    @SaCheckPermission("news:comment:pass")
    @PutMapping("/admin/comment/pass")
    public Result<?> updateCommentCheck(@Validated @RequestBody CheckReq check) {
        commentService.updateCommentCheck(check);
        return Result.success();
    }

    /**
     * 点赞评论
     *
     * @param commentId 评论id
     * @return {@link Result<>}
     */
    @SaCheckLogin
    @ApiOperation(value = "点赞评论")
    @AccessLimit(seconds = 60, maxCount = 3)
    @SaCheckPermission("news:comment:like")
    @PostMapping("/comment/{commentId}/like")
    public Result<?> likeComment(@PathVariable("commentId") Integer commentId) {
        likeStrategyContext.executeLikeStrategy(LikeTypeEnum.COMMENT, commentId);
        return Result.success();
    }

    /**
     * 查看最新评论
     *
     * @return {@link List<   RecentCommentResp   >}
     */
    @ApiOperation(value = "查看最新评论")
    @GetMapping("/recent/comment")
    public Result<List<RecentCommentResp>> listRecentCommentVO() {
        return Result.success(commentService.listRecentCommentVO());
    }

    /**
     * 查看评论
     *
     * @param commentQuery 条件
     * @return {@link Result<  CommentResp  >}
     */
    @ApiOperation(value = "查看评论")
    @GetMapping("/comment/list")
    public Result<PageResult<CommentResp>> listCommentVO(CommentQuery commentQuery) {
        return Result.success(commentService.listCommentVO(commentQuery));
    }

    /**
     * 查看回复评论
     *
     * @param commentId 评论id
     * @return {@link Result< ReplyResp >} 回复评论列表
     */
    @ApiOperation(value = "查看回复评论")
    @GetMapping("/comment/{commentId}/reply")
    public Result<List<ReplyResp>> listReply(@PathVariable("commentId") Integer commentId) {
        return Result.success(commentService.listReply(commentId));
    }

}
