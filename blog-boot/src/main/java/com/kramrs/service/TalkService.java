package com.kramrs.service;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kramrs.entity.Talk;
import com.kramrs.enums.FilePathEnum;
import com.kramrs.mapper.CommentMapper;
import com.kramrs.mapper.TalkMapper;
import com.kramrs.model.vo.PageResult;
import com.kramrs.model.vo.query.PageQuery;
import com.kramrs.model.vo.query.TalkQuery;
import com.kramrs.model.vo.request.TalkReq;
import com.kramrs.model.vo.response.CommentCountResp;
import com.kramrs.model.vo.response.TalkBackInfoResp;
import com.kramrs.model.vo.response.TalkBackResp;
import com.kramrs.model.vo.response.TalkResp;
import com.kramrs.strategy.context.UploadStrategyContext;
import com.kramrs.utils.BeanCopyUtils;
import com.kramrs.utils.CommonUtils;
import com.kramrs.utils.HTMLUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.kramrs.constant.RedisConstant.TALK_LIKE_COUNT;
import static com.kramrs.enums.ArticleStatusEnum.PUBLIC;
import static com.kramrs.enums.CommentTypeEnum.TALK;

/**
 * @Author: kramrs
 * @Description: 说说服务
 */
@Service
public class TalkService extends ServiceImpl<TalkMapper, Talk> {

    @Autowired
    private TalkMapper talkMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UploadStrategyContext uploadStrategyContext;

    @Autowired
    private BlogFileService blogFileService;

    public PageResult<TalkBackResp> listTalkBackVO(TalkQuery talkQuery) {
        // 查询说说数量
        Long count = talkMapper.selectCount(new LambdaQueryWrapper<Talk>()
                .eq(Objects.nonNull(talkQuery.getStatus()), Talk::getStatus, talkQuery.getStatus()));
        if (count == 0) {
            return new PageResult<>();
        }
        // 分页查询说说列表
        List<TalkBackResp> talkBackRespList = talkMapper.selectBackTalkList(talkQuery);
        talkBackRespList.forEach(item -> {
            // 转换图片格式
            if (Objects.nonNull(item.getImages())) {
                item.setImgList(CommonUtils.castList(JSON.parseObject(item.getImages(), List.class), String.class));
            }
        });
        return new PageResult<>(talkBackRespList, count);
    }

    public void addTalk(TalkReq talk) {
        Talk newTalk = BeanCopyUtils.copyBean(talk, Talk.class);
        newTalk.setUserId(StpUtil.getLoginIdAsInt());
        baseMapper.insert(newTalk);
    }

    public void deleteTalk(Integer talkId) {
        talkMapper.deleteById(talkId);
    }

    public void updateTalk(TalkReq talk) {
        Talk newTalk = BeanCopyUtils.copyBean(talk, Talk.class);
        newTalk.setUserId(StpUtil.getLoginIdAsInt());
        baseMapper.updateById(newTalk);
    }

    public TalkBackInfoResp editTalk(Integer talkId) {
        TalkBackInfoResp talkBackVO = talkMapper.selectTalkBackById(talkId);
        // 转换图片格式
        if (Objects.nonNull(talkBackVO.getImages())) {
            talkBackVO.setImgList(CommonUtils.castList(JSON.parseObject(talkBackVO.getImages(), List.class), String.class));
        }
        return talkBackVO;
    }

    public List<String> listTalkHome() {
        // 查询最新5条说说
        List<Talk> talkList = talkMapper.selectList(new LambdaQueryWrapper<Talk>()
                .select(Talk::getTalkContent)
                .eq(Talk::getStatus, PUBLIC.getStatus())
                .orderByDesc(Talk::getIsTop)
                .orderByDesc(Talk::getId)
                .last("limit 5"));
        return talkList.stream()
                .map(item -> item.getTalkContent().length() > 200
                        ? HTMLUtils.deleteHtmlTag(item.getTalkContent().substring(0, 200))
                        : HTMLUtils.deleteHtmlTag(item.getTalkContent()))
                .collect(Collectors.toList());
    }

    public PageResult<TalkResp> listTalkVO(PageQuery pageQuery) {
        // 查询说说总量
        Long count = talkMapper.selectCount((new LambdaQueryWrapper<Talk>()
                .eq(Talk::getStatus, PUBLIC.getStatus())));
        if (count == 0) {
            return new PageResult<>();
        }
        // 分页查询说说
        List<TalkResp> talkRespList = talkMapper.selectTalkList(pageQuery);
        // 查询说说评论量
        List<Integer> talkIdList = talkRespList.stream()
                .map(TalkResp::getId)
                .collect(Collectors.toList());
        List<CommentCountResp> commentCountVOList = commentMapper.selectCommentCountByTypeId(talkIdList, TALK.getType());
        Map<Integer, Integer> commentCountMap = commentCountVOList.stream()
                .collect(Collectors.toMap(CommentCountResp::getId, CommentCountResp::getCommentCount));
        // 查询说说点赞量
        Map<String, Integer> likeCountMap = redisService.getHashAll(TALK_LIKE_COUNT);
        // 封装说说
        talkRespList.forEach(item -> {
            item.setLikeCount(Optional.ofNullable(likeCountMap.get(item.getId().toString())).orElse(0));
            item.setCommentCount(Optional.ofNullable(commentCountMap.get(item.getId())).orElse(0));
            // 转换图片格式
            if (Objects.nonNull(item.getImages())) {
                item.setImgList(CommonUtils.castList(JSON.parseObject(item.getImages(), List.class), String.class));
            }
        });
        return new PageResult<>(talkRespList, count);
    }

    public TalkResp getTalkById(Integer talkId) {
        // 查询说说信息
        TalkResp talkResp = talkMapper.selectTalkById(talkId);
        if (Objects.isNull(talkResp)) {
            return null;
        }
        // 查询说说点赞量
        Integer likeCount = redisService.getHash(TALK_LIKE_COUNT, talkId.toString());
        talkResp.setLikeCount(Optional.ofNullable(likeCount).orElse(0));
        // 转换图片格式
        if (Objects.nonNull(talkResp.getImages())) {
            talkResp.setImgList(CommonUtils.castList(JSON.parseObject(talkResp.getImages(), List.class), String.class));
        }
        return talkResp;
    }

    public String uploadTalkCover(MultipartFile file) {
        // 上传文件
        String url = uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.TALK.getPath());
        blogFileService.saveBlogFile(file, url, FilePathEnum.TALK.getFilePath());
        return url;
    }
}
