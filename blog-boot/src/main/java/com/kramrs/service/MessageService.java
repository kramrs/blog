package com.kramrs.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kramrs.constant.CommonConstant;
import com.kramrs.entity.Message;
import com.kramrs.entity.SiteConfig;
import com.kramrs.mapper.MessageMapper;
import com.kramrs.model.vo.PageResult;
import com.kramrs.model.vo.query.MessageQuery;
import com.kramrs.model.vo.request.CheckReq;
import com.kramrs.model.vo.request.MessageReq;
import com.kramrs.model.vo.response.MessageBackResp;
import com.kramrs.model.vo.response.MessageResp;
import com.kramrs.utils.BeanCopyUtils;
import com.kramrs.utils.HTMLUtils;
import com.kramrs.utils.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: kramrs
 * @Description: 留言业务接口实现类
 */
@Service
public class MessageService extends ServiceImpl<MessageMapper, Message> {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SiteConfigService siteConfigService;

    public List<MessageResp> listMessageVO() {
        // 查询留言列表
        return messageMapper.selectMessageVOList();
    }

    public PageResult<MessageBackResp> listMessageBackVO(MessageQuery messageQuery) {
        // 查询留言数量
        Long count = messageMapper.selectCount(new LambdaQueryWrapper<Message>()
                .like(StringUtils.hasText(messageQuery.getKeyword()), Message::getNickname, messageQuery.getKeyword())
                .eq(Objects.nonNull(messageQuery.getIsCheck()), Message::getIsCheck, messageQuery.getIsCheck()));
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询后台友链列表
        List<MessageBackResp> messageBackRespList = messageMapper.selectBackMessageList(messageQuery);
        return new PageResult<>(messageBackRespList, count);
    }

    public void addMessage(MessageReq message) {
        SiteConfig siteConfig = siteConfigService.getSiteConfig();
        Integer messageCheck = siteConfig.getMessageCheck();
        String ipAddress = IpUtils.getIpAddress(request);
        String ipSource = IpUtils.getIpSource(ipAddress);
        Message newMessage = BeanCopyUtils.copyBean(message, Message.class);
        newMessage.setMessageContent(HTMLUtils.filter(message.getMessageContent()));
        newMessage.setIpAddress(ipAddress);
        newMessage.setIsCheck(messageCheck.equals(CommonConstant.FALSE) ? CommonConstant.TRUE : CommonConstant.FALSE);
        newMessage.setIpSource(ipSource);
        messageMapper.insert(newMessage);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateMessageCheck(CheckReq check) {
        // 修改留言审核状态
        List<Message> messageList = check.getIdList()
                .stream()
                .map(id -> Message.builder()
                        .id(id)
                        .isCheck(check.getIsCheck())
                        .build())
                .collect(Collectors.toList());
        this.updateBatchById(messageList);
    }
}
